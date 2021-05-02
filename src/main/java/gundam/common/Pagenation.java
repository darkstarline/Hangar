package gundam.common;

import com.alibaba.fastjson.JSON;

public class Pagenation {
    private static final long serialVersionUID = -3059503802484247300L;

    public static final int PAGE_SIZE_DEFAULT = 15;
    public static final int PAGES_PER_TIME_DEFAULT = 10;

    private int pageSize = PAGE_SIZE_DEFAULT;
    private int pagesPerTime = PAGES_PER_TIME_DEFAULT;

    private int totalSize = 0;

    private int curPage = 1;
    private int totalPage = 1;

    private int startPage = 1;
    private int endPage = 1;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPagesPerTime() {
        return pagesPerTime;
    }
    public void setPagesPerTime(int pagesPerTime) {
        this.pagesPerTime = pagesPerTime;
    }
    public int getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        this.setTotalPage((totalSize - 1)/this.pageSize + 1);
    }
    public int getCurPage() {
        return curPage;
    }
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getStartPage() {
        if(this.curPage >= this.totalPage - this.getPagesPerTime()/2){
            startPage = this.totalPage - this.getPagesPerTime() +1;
        }else{
            startPage = this.curPage - this.getPagesPerTime()/2;
        }
        if(startPage < 1){
            startPage = 1;
        }
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        endPage = this.getStartPage() + this.getPagesPerTime() - 1;
        if(endPage > this.totalPage){
            endPage = this.totalPage;
        }
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getStartIndex(){
        return (this.curPage-1) * this.pageSize;
    }

    public int getEndIndex(){
        int endIndex = this.curPage * this.pageSize;
        if(endIndex > this.totalSize){
            endIndex = this.totalSize;
        }
        return endIndex;
    }

    public void prepare(){
        if(0 == this.curPage){
            this.setCurPage(1);
        }
    }
}
