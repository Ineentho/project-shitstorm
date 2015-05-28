#!/usr/bin/env sh

export source='./desktop/build/libs/desktop-1.0.jar'
export server='shitstorm@opid.io:~/releases/'

shortHash() {
    git rev-parse --short HEAD
}

isoDate() {
    date +%Y%m%dT%H%M%S
}

fileName() {
    echo project-shitstorm-reloaded-$(isoDate)-$(shortHash).jar
}

scp ${source} ${server}$(fileName)