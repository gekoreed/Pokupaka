#!/usr/bin/env bash

ffmpeg -rtsp_transport tcp -i rtsp://rtsp.cdn.ua/bukovel.com_camera/_definst_/04_720p.stream -an -vframes 1 -s 300*400 pi.jpg
echo "\n\nFucking server has done the fucking picture "
echo $2
echo "\n\n"
                                   ## 300*300