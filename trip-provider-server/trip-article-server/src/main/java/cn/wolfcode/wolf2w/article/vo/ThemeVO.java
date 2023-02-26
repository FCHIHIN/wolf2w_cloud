package cn.wolfcode.wolf2w.article.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ThemeVO {
    private String  themeName;
    private List<DestVO> dests = new ArrayList<>();

    public List<DestVO> parseList(String destids,String destnames){
        List<DestVO> list = new ArrayList<>();
        if(StringUtils.hasLength(destids)){
            String[] ids = destids.split(",");
            String[] names = destnames.split(",");
            if(ids != null && ids.length > 0){
                for (int i = 0;i <ids.length; i++) {
                    DestVO destVO = new DestVO();
                    destVO.setId(Long.parseLong(ids[i]));
                    destVO.setName(names[i]);
                    list.add(destVO);
                }
            }
        }
        return list;
    }
}
