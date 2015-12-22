package com.selfach.processor.handlers.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.CamerasDao;
import com.selfach.dao.jooq.tables.records.CameraRecord;
import com.selfach.dao.jooq.tables.records.CameragroupRecord;
import com.selfach.processor.handlers.GeneralHandler;
import com.selfach.processor.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.GeneralPath;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by gekoreed on 12/20/15.
 */
@Component("cameraGroups")
public class CameraGroupsHandler implements GeneralHandler<CameraGroupsHandler.ResponseGroup> {

    @Autowired
    CamerasDao camerasDao;

    @Override
    public ResponseGroup handle(ObjectNode node) throws Exception {
        String lang = node.has("lang") ? node.get("lang").asText() : "ua";
        List<CameragroupRecord> cameras = camerasDao.getCameraGroups(lang);
        List<Group> collect = cameras.stream()
                .map(this::convert)
                .map(g -> langCheck(g, lang))
                .collect(toList());


        return new ResponseGroup(collect);
    }

    private Group langCheck(Group group, String lang) {
        switch (lang){
            case "en":
                group.name = group.name.split(";")[2];
                break;
            case "ua":
                group.name = group.name.split(";")[1];
                break;
            case "ru":
                group.name = group.name.split(";")[0];
                break;
        }
        return group;
    }

    private Group convert(CameragroupRecord record) {
        Group g = new Group();
        g.id = record.getId();
        g.name = record.getName();
        return g;
    }

    class Group{
        public int id;
        public String name;
    }

    public class ResponseGroup extends Response {

        public ResponseGroup(List<Group> groups) {
            this.groups = groups;
        }

        public List<Group> groups;
    }
}
