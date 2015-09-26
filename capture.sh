#!/usr/bin/env bash

ffmpeg -i $1 -an -vframes 1 -s 700x400 pictures/$2.jpg
