#!/usr/bin/env bash

ffmpeg -i '%s' -an -vframes 1 -s 700x400 pictures/%s.jpg
