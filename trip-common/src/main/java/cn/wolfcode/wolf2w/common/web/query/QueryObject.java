package cn.wolfcode.wolf2w.common.web.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 10;

    private String keyword;

}
