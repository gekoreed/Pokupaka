package com.selfach;

import com.selfach.dao.CamerasDao;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * By gekoreed on 9/12/15.
 */
@Component
@ChannelHandler.Sharable
public class HttpResolver extends SimpleChannelInboundHandler<HttpContent> {

    @Autowired
    CamerasDao camerasDao;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {

        String answer = "Server is OK\n";
        String db = "";
        try {
            camerasDao.getAvailableCameras();
        }catch (Exception e){
            db += "Database is DOWN";
        }
        if (db.equals(""))
            db = "Database is OK";
        new MsgResolver().writeAnswer(ctx, answer+db);

    }

}
