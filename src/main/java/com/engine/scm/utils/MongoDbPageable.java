//package com.kuandeng.data.utils;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.io.Serializable;
//
///**
// * 分页
// * @author jiang
// */
//@Getter
//@Setter
//public class MongoDbPageable implements Serializable, Pageable {
//    private Integer pagenumber = 1;
//    private Integer pagesize = 10;
//    private Sort sort;
//
//    @Override
//    public int getPageNumber() {
//        return getPagenumber();
//    }
//
//    @Override
//    public int getPageSize() {
//        return getPagesize();
//    }
//
//    @Override
//    public long getOffset() {
//        return (getPagenumber() - 1) * getPagesize();
//    }
//
//    @Override
//    public Sort getSort() {
//        return sort;
//    }
//
//    public Integer getPagenumber() {
//        return pagenumber;
//    }
//
//    public void setPagenumber(Integer pagenumber) {
//        this.pagenumber = pagenumber;
//    }
//
//    public Integer getPagesize() {
//        return pagesize;
//    }
//
//    public void setPagesize(Integer pagesize) {
//        this.pagesize = pagesize;
//    }
//
//    public void setSort(Sort sort) {
//        this.sort = sort;
//    }
//
//    @Override
//    public Pageable first() {
//        return null;
//    }
//
//    @Override
//    public boolean hasPrevious() {
//        return false;
//    }
//
//    @Override
//    public Pageable next() {
//        return null;
//    }
//
//    @Override
//    public Pageable previousOrFirst() {
//        return null;
//    }
//}
