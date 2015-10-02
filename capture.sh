#!/usr/bin/env bash

ffmpeg -i $1 -an -vframes 1 -s $3 pictures/$2.jpg
echo "\n\nFucking server has done the fucking picture "
echo $2
echo "\n\n"
                                   ## 300*300