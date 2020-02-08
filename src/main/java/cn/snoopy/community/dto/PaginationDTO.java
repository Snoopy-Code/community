package cn.snoopy.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious; //上一页接头
    private boolean showFirstPage; //回到第一页箭头
    private boolean showNext;//下一页箭头
    private boolean showEndPage;//回到最后一页箭头
    private Integer page;//当前页
    private List<Integer> pages = new ArrayList<>(); //5个页面的页码，因为size为5
    private Integer totalPage;//总页数

    public void setPagination(Integer totalPage, Integer page) {
       this.totalPage=totalPage;
        this.page=page;
        pages.add(page);
        for(int i =1; i<=3;i++){
            //显示当前页
            if(page-i>0){
                pages.add(0,page-i);
            }
            //往当前页后面添加页码
            if((page+i<=totalPage)){
                pages.add(page+i);
            }
        }
        //是否展示上一页箭头
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //是否展示下一页箭头
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }
        //是否展示到第一页箭头
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //是否展示到最后一页箭头
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
