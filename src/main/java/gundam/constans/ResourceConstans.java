package gundam.constans;

public final class ResourceConstans {

    public static final class OPERATOR {
        public static final int ADMIN_YES = 1;
        public static final boolean LOCK_FLAG_YES = true;
        public static final int LOCK_FLAG_NO = 0;
        public static final String STATE_USED = "U";
        public static final long STATE_EXPIRED = 0;
        public static final String ROLE_ADMIN = "admin";
        public static final String ROLE_USER = "user";
        public static final String ROLE_ADMIN_BS_STATIC_DATE_TYPE = "CMS_ROLE_ADMIN";
        public static final String ROLE_USER_BS_STATIC_DATE_TYPE = "CMS_ROLE_USER";
    }
    public static final class STATE {
        public static final String USED = "U";
        public static final String EXPIRE = "E";
    }
    public static final class FILESTATUS {
        /** 等待上传, 第一次上传接口调用后的状态 */
        public static final String WAIT_UPLOAD = "WAIT_UPLOAD";

        /** 上传成功，使用中 */
        public static final String IN_USE = "IN_USE";

        /**  已归档，被新版本覆盖 */
        public static final String ARCHIVED = "ARCHIVED";

        /** 标记删除，文件不再使用，但文件依然存储在文件服务器上，尚未删除 */
        public static final String MARK_DELETE = "MARK_DELETE";

        /** 已删除，文件存储转移到磁带 */
        public static final String DELETED = "DELETED";
    }
}
