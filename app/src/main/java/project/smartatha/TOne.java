package project.smartatha;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

public class TOne extends Thread {
    private Thread T1;
    private byte audiobuffer[] = new byte[20];
    public boolean Okay = true;
    public AudioRecord a;
    public AudioTrack aud;
    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
    @Override
    public void run() {
        // TODO Auto-generated method stub
        //super.run();
        int i=AudioRecord.getMinBufferSize(8000 , AudioFormat.CHANNEL_IN_MONO ,      AudioFormat.ENCODING_PCM_16BIT);
        a= findAudioRecord();

        if(a.getState() != AudioRecord.STATE_INITIALIZED){
            a= findAudioRecord();
        }
        a.startRecording();
        Log.e("Play Audio" , "Start");

        aud = new AudioTrack(AudioManager.STREAM_MUSIC,8000,
                AudioFormat.CHANNEL_OUT_MONO,   AudioFormat.ENCODING_PCM_16BIT,AudioTrack.getMinBufferSize(8000,AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT),AudioTrack.MODE_STREAM);

        if(aud.getState() != AudioTrack.STATE_INITIALIZED){
            aud = new AudioTrack(AudioManager.STREAM_MUSIC,8000,
                    AudioFormat.CHANNEL_OUT_MONO,      AudioFormat.ENCODING_PCM_16BIT,AudioTrack.getMinBufferSize(8000,AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT),AudioTrack.MODE_STREAM);

        }
        aud.play();
        while(Okay){

            a.read(audiobuffer,0,audiobuffer.length);

            ////Log.e("IS_ACTIVE", "YES");
            aud.write(audiobuffer,0,audiobuffer.length);

        }

    }

    public AudioRecord findAudioRecord() {

        for (int rate : mSampleRates) {
            for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_8BIT,      AudioFormat.ENCODING_PCM_16BIT }) {
                for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO,      AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        Log.d("C.TAG", "Attempting rate " + rate + "Hz, bits: " + audioFormat +      ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate,      AudioFormat.CHANNEL_IN_MONO , AudioFormat.ENCODING_PCM_16BIT);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(AudioSource.MIC, 8000,      channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        Log.e("C.TAG", rate + "Exception, keep trying.",e);
                    }
                }
            }
        }
        return null;
    }

    private void LetsPlayThatsAudio() {
// TODO Auto-generated method stub
    }
}