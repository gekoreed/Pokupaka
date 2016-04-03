package com.selfach;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.annotations.Dev;
import com.selfach.dao.CamerasDao;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ConcurrentSet;
import org.apache.log4j.Logger;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.selfach.processor.handlers.FileUtil.loadFile;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * By gekoreed on 9/12/15.
 */
@Component
@ChannelHandler.Sharable
public class MsgResolver extends SimpleChannelInboundHandler<Object> {
    String APPLICATION_JSON = "application/json; charset=UTF-8";
    String APPLICATION_ZIP = "application/jpg";

    Logger logger = Logger.getLogger(MsgResolver.class);

    final ObjectMapper mapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    Map<String, GeneralHandler> beansOfType;

    @Autowired
    ApplicationContext context;

    @Value("${server.env}")
    String env;

    @Autowired
    CamerasDao camerasDao;
    private Set<Channel> sockets = new ConcurrentSet<>();

    @PostConstruct
    public void preConstruct(){
        beansOfType = context.getBeansOfType(GeneralHandler.class);

        if  (env.toLowerCase().equals("prod")){
            for (Iterator<GeneralHandler> it = beansOfType.values().iterator(); it.hasNext(); ) {
                if (it.next().getClass().isAnnotationPresent(Dev.class)) {
                    it.remove();
                }
            }
        }
    }

    private void responseToAll(String requestContent) {
        for (Channel webSocketChannel : sockets) {
            webSocketChannel.write(new TextWebSocketFrame("Answer: " + requestContent));
            webSocketChannel.flush();
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            if (req.getMethod().equals(HttpMethod.GET)) {
                handleGetRequests(ctx, req);
                return;
            }
        } else if (msg instanceof WebSocketFrame) {
            if (msg instanceof TextWebSocketFrame) {
                TextWebSocketFrame webSocketFrame = (TextWebSocketFrame) msg;
                String requestContent = webSocketFrame.text();
                sockets.add(ctx.channel());
                responseToAll(requestContent);
            }
        }
        ByteBuf buf = ((HttpContent) msg).content();
        String requestContent = buf.toString(CharsetUtil.UTF_8);
        ObjectNode requestNode = (ObjectNode) mapper.readTree(requestContent);
        if (!requestNode.has("cmd")) {
            throw new AndroidServerException("Your JSON not have command");
        }

        String cmd = requestNode.get("cmd").asText();
        if (!beansOfType.keySet().contains(cmd))
            throw new AndroidServerException("unknown command");

        GeneralHandler handler = beansOfType.get(cmd);

        Response response = handler.handle(requestNode);
        JsonNode node = mapper.valueToTree(response);

        writeAnswer(ctx, node);
    }

    private void handleGetRequests(ChannelHandlerContext ctx, HttpRequest req) throws AndroidServerException {
        String uri = req.getUri().substring(1);
        if (uri.startsWith("last")) {
            uri = uri + ".jpg";
        } else if (uri.contains("websocket")) {
            ReferenceCountUtil.retain(req);
            ctx.fireChannelRead(req);
            return;
        }
        File picture = new File(uri);
        if (picture.exists())
            writeAnswer(ctx, picture);
        else
            throw new AndroidServerException("picture_doesnt_exists");
    }

    private void writeAnswer(ChannelHandlerContext ctx, JsonNode jsonNode) {
        writeAnswer(ctx, jsonNode.toString());
    }

    public void writeAnswer(ChannelHandlerContext ctx, String answer) {
        write(ctx, answer.getBytes(Charset.forName("UTF-8")), APPLICATION_JSON);
    }

    private void writeAnswer(ChannelHandlerContext ctx, File file) {
        write(ctx, loadFile(file), APPLICATION_ZIP);
    }

    private void write(ChannelHandlerContext ctx, byte[] bytes, String content_type){
        ByteBuf respContent = ctx.alloc().buffer();
        respContent.writeBytes(bytes);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, respContent);
        response.headers().set(CONTENT_TYPE, content_type);
        response.headers().set(CONTENT_LENGTH, respContent.readableBytes());
        ctx.writeAndFlush(response);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        class ExResponse extends Response{
            public String content;
        }

        logger.debug(cause.getMessage());

        if (cause instanceof DataAccessException)
            logger.debug("ERROR: \n\t\t\t\t" + cause.getMessage());

        ExResponse exresponse = new ExResponse();
        exresponse.content = cause.getMessage();
        exresponse.result = 0;

        String errorBody = mapper.writeValueAsString(exresponse);

        writeAnswer(ctx, errorBody);

    }
}