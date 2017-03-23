package maqianqian.amiao.com.newstodaydemo.bean;

/**
 * Created by lenovo on 2017/3/21.
 */

public class ContainBean {
    public int id;
    public String image;
    public String url;
    public String title;

    @Override
    public String toString() {
        return "ContainBean{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
