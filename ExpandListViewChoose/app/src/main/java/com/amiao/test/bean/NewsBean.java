package com.amiao.test.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/12.
 */

public class NewsBean {

    /**
     * code : 200
     * flag : Success
     * msg :
     * data : [{"title_id":"91587","title":"64646464","datas":[{"cart_id":"379433","price":370,"type_sn_id":"846494465956599","house_id":"入企业库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 12:20:38","type_name":"中兴 AXON 天机 mini（B2015 压力屏版）"},{"cart_id":"379436","price":465,"type_sn_id":"946449434634496","house_id":"入易回购库","msg":["购买渠道:香港行货","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 12:20:53","type_name":"HTC Desire 10 Pro（全网通/32G）"}]},{"title_id":"91586","title":"542424242424","datas":[{"cart_id":"379421","price":515,"type_sn_id":"858683372738252","house_id":"入企业库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:浸液/受潮"],"add_time":"2017-04-10 12:19:29","type_name":"OPPO R9 Plus（高配版/全网通）"},{"cart_id":"379423","price":3,"type_sn_id":"542425682424575","house_id":"入易回购库","msg":["购买渠道:大陆国行","开机情况:能开机"],"add_time":"2017-04-10 12:19:41","type_name":"IUNI U2"}]},{"title_id":"91585","title":"45566866","datas":[{"cart_id":"379414","price":240,"type_sn_id":"858555555555555","house_id":"入企业库","msg":["边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:色差/亮点/轻微发黄","拆修情况:外壳/配件维修","存在以下问题:icloud无法注销"],"add_time":"2017-04-10 12:18:33","type_name":"苹果 iPhone 7 plus"},{"cart_id":"379415","price":1540,"type_sn_id":"458869666985584","house_id":"入企业库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:外壳/配件维修","存在以下问题:机身变形"],"add_time":"2017-04-10 12:18:48","type_name":"三星 W2016+"},{"cart_id":"379416","price":1360,"type_sn_id":"757653535242768","house_id":"入易回购库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 12:19:02","type_name":"LG G5（全网通）"}]},{"title_id":"91581","title":"494664","datas":[{"cart_id":"379184","price":360,"type_sn_id":"646446496494646","house_id":"入易回购库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 11:32:35","type_name":"华硕 ZenFone 飞马3（高配版/32G/全网通/ASUS X008）"}]}]
     */

    private String code;
    private String flag;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title_id : 91587
         * title : 64646464
         * datas : [{"cart_id":"379433","price":370,"type_sn_id":"846494465956599","house_id":"入企业库","msg":["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 12:20:38","type_name":"中兴 AXON 天机 mini（B2015 压力屏版）"},{"cart_id":"379436","price":465,"type_sn_id":"946449434634496","house_id":"入易回购库","msg":["购买渠道:香港行货","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"],"add_time":"2017-04-10 12:20:53","type_name":"HTC Desire 10 Pro（全网通/32G）"}]
         */

        private String title_id;
        private String title;
        private List<DatasBean> datas;
        public boolean falg_p=false;

        public String getTitle_id() {
            return title_id;
        }

        public void setTitle_id(String title_id) {
            this.title_id = title_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * cart_id : 379433
             * price : 370
             * type_sn_id : 846494465956599
             * house_id : 入企业库
             * msg : ["购买渠道:大陆国行","边框背板:全新原封机","屏幕外观:屏幕完好","屏幕性能:屏幕性能完好","拆修情况:无拆修","存在以下问题:全部正常"]
             * add_time : 2017-04-10 12:20:38
             * type_name : 中兴 AXON 天机 mini（B2015 压力屏版）
             */

            private String cart_id;
            private int price;
            private String type_sn_id;
            private String house_id;
            private String add_time;
            private String type_name;
            private List<String> msg;
            public boolean flag_c=false;

            public String getCart_id() {
                return cart_id;
            }

            public void setCart_id(String cart_id) {
                this.cart_id = cart_id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getType_sn_id() {
                return type_sn_id;
            }

            public void setType_sn_id(String type_sn_id) {
                this.type_sn_id = type_sn_id;
            }

            public String getHouse_id() {
                return house_id;
            }

            public void setHouse_id(String house_id) {
                this.house_id = house_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public List<String> getMsg() {
                return msg;
            }

            public void setMsg(List<String> msg) {
                this.msg = msg;
            }
        }
    }
}
