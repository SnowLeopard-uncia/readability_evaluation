package com.example.bamboo.javaBean;

public class ColorcoverDTO {
    private String _type;
        private String cdn;
        private String filename;
        private String url;

        public String getType() {
            return _type;
        }

        public void setType(String type) {
            this._type = type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
