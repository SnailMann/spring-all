package com.snailmann.me.mp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * 分页公共对象
 *
 * @param <T>
 * @author liwenjie
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResp<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long pageCount;

    /**
     * 当前页数, 起始位 为 1
     */
    private Integer page;

    /**
     * 条数偏移量，起始位 为 0
     */
    private Integer offset;

    /**
     * 每页的大小
     */
    private Integer limit;

    /**
     * 下一页
     */
    private Integer nextPage;

    /**
     * 下一条
     */
    private Integer nextOffset;

    /**
     * 数据
     */
    List<T> data;

    private PageResp(Long totalCount, List<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public static <T> PageResp<T> empty() {
        return PageResp.of(0L, Collections.emptyList());
    }

    public static <T> PageResp<T> of(List<T> data) {
        return new PageResp<>(null, data);
    }

    public static <T> PageResp<T> of(Long totalCount, List<T> data) {
        return new PageResp<>(totalCount, data);
    }

    public static <T> PageResp<T> of(Long totalCount, Long pageCount, Integer page, Integer offset,
                                     Integer limit, List<T> data) {
        return new PageResp<>(totalCount, pageCount, page, offset,
                limit, null, null, data);
    }

    public static <T> PageResp<T> of(Long totalCount, Long pageCount, Integer page, Integer offset,
                                     Integer nextPage, Integer nextOffset,
                                     Integer limit, List<T> data) {
        return new PageResp<>(totalCount, pageCount, page, offset,
                limit, nextPage, nextOffset, data);
    }

    public static <T> PageResp<T> pageOf(Long totalCount, Long pageCount, Integer page,
                                         Integer nextPage,
                                         Integer limit, List<T> data) {
        return new PageResp<>(totalCount, pageCount, page, null,
                limit, nextPage, null, data);
    }

    public static <T> PageResp<T> offsetOf(Long totalCount, Integer offset, Integer nextOffset,
                                           Integer limit,
                                           List<T> data) {
        return new PageResp<>(totalCount, null, null,
                offset, limit, null, nextOffset, data);
    }

    public <R> PageResp<R> map(Function<? super T, ? extends R> mapper) {
        List<R> data = getConvertedContent(mapper);
        return PageResp.of(this.totalCount, this.pageCount, this.page, this.offset, this.limit, data);
    }

    private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        if (converter == null) {
            throw new IllegalArgumentException("Function must not be null!");
        }
        return this.data.stream().map(converter).collect(Collectors.toList());
    }

    public PageResp<T> filter(Predicate<? super T> predicate) {
        List<T> data = Optional.ofNullable(this.getData())
                .orElse(Collections.emptyList())
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return PageResp.of(this.totalCount, this.pageCount, this.page, this.offset, this.limit, data);
    }

    public Iterator<T> iterator() {
        return data.iterator();
    }

    public void forEach(Consumer<? super T> action) {
        data.forEach(action);
    }

    public Spliterator<T> spliterator() {
        return data.spliterator();
    }

    public static class PageRespUtils {

        public boolean isNotEmpty(PageResp pageResp) {
            if (pageResp == null) {
                return false;
            }
            return !CollectionUtils.isEmpty(pageResp.data);
        }

        public boolean isEmpty(PageResp pageResp) {
            if (pageResp == null) {
                return true;
            }
            return CollectionUtils.isEmpty(pageResp.data);
        }
    }

}
