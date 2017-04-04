package bean;

import java.util.List;

/**
 * Created by lenovo on 2017/3/31.
 */

public class NewsBean {

    /**
     * cname : 热门分类
     * nodes : [{"id":"44","category_name":"美妆美发","category_fid":"9","category_order":"0","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"1"},{"id":"45","category_name":"服饰","category_fid":"9","category_order":"0","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"1"},{"id":"48","category_name":"美甲","category_fid":"9","category_order":"0","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"1"},{"id":"74","category_name":"星座","category_fid":"15","category_order":"0","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"3"},{"id":"211","category_name":"摄影","category_fid":"15","category_order":"40","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"3"},{"id":"212","category_name":"舞蹈","category_fid":"15","category_order":"41","category_status":"1","category_lever":"3","category_p":null,"category_ishot":"1","category_fiid":"3"}]
     */

    private String cname;
    private List<NodesBean> nodes;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public static class NodesBean {
        /**
         * id : 44
         * category_name : 美妆美发
         * category_fid : 9
         * category_order : 0
         * category_status : 1
         * category_lever : 3
         * category_p : null
         * category_ishot : 1
         * category_fiid : 1
         */

        private String id;
        private String category_name;
        private String category_fid;
        private String category_order;
        private String category_status;
        private String category_lever;
        private Object category_p;
        private String category_ishot;
        private String category_fiid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_fid() {
            return category_fid;
        }

        public void setCategory_fid(String category_fid) {
            this.category_fid = category_fid;
        }

        public String getCategory_order() {
            return category_order;
        }

        public void setCategory_order(String category_order) {
            this.category_order = category_order;
        }

        public String getCategory_status() {
            return category_status;
        }

        public void setCategory_status(String category_status) {
            this.category_status = category_status;
        }

        public String getCategory_lever() {
            return category_lever;
        }

        public void setCategory_lever(String category_lever) {
            this.category_lever = category_lever;
        }

        public Object getCategory_p() {
            return category_p;
        }

        public void setCategory_p(Object category_p) {
            this.category_p = category_p;
        }

        public String getCategory_ishot() {
            return category_ishot;
        }

        public void setCategory_ishot(String category_ishot) {
            this.category_ishot = category_ishot;
        }

        public String getCategory_fiid() {
            return category_fiid;
        }

        public void setCategory_fiid(String category_fiid) {
            this.category_fiid = category_fiid;
        }
    }
}
