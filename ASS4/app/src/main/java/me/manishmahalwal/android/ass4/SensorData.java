package me.manishmahalwal.android.ass4;

/**
 * Created by Manish on 01-11-2018.
 * manish16054@iiitd.ac.in
 */
public class SensorData {

    public int id;
    public String data;
    public String mtimestamp;

    public SensorData(int id, String mtimestamp, String data) {
        this.id = id;
        this.mtimestamp = mtimestamp;
        this.data = data;
    }

}
