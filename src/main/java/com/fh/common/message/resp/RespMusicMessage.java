package com.fh.common.message.resp;

/**
 * Created by xulu on 2016/3/7.
 */
public class RespMusicMessage extends RespBaseMessage{

    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
