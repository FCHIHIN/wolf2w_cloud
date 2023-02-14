package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Region;
import cn.wolfcode.wolf2w.article.mapper.DestinationMapper;
import cn.wolfcode.wolf2w.article.mapper.RegionMapper;
import cn.wolfcode.wolf2w.article.query.DestinationQuery;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import cn.wolfcode.wolf2w.article.service.IDestinationService;
import cn.wolfcode.wolf2w.article.service.IRegionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DestinationServiceimpl extends ServiceImpl<DestinationMapper, Destination> implements IDestinationService {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private DestinationMapper destinationMapper;

    @Override
    public List<Destination> viewDestination(Long rid) {
        Region region = regionService.getById(rid);
        List<Long> ids = region.parseRefIds();
        return super.listByIds(ids);
    }

    @Override
    public Page<Destination> queryPage(DestinationQuery qo) {
        Page<Destination> page = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        LambdaQueryWrapper<Destination> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getKeyword()),Destination::getName,qo.getKeyword());
        wrapper.eq(qo.getParentId()!=null,Destination::getParentId,qo.getParentId());
        wrapper.isNull(qo.getParentId()==null,Destination::getParentId);
        return super.page(page,wrapper);
    }

    @Override
    public List<Destination> toasts(Long destId) {
        List<Destination> list = new ArrayList<>();
        // 递归
        createToasts(list,destId);
        Collections.reverse(list);
        return list;
    }

    public void createToasts(List list,Long destId) {
        if (destId == null) {
            return;
        }
        Destination destination = super.getById(destId);
        list.add(destination);
        if (destination.getParentId() != null) {
            this.createToasts(list,destination.getParentId());
        }

    }

    @Override
    public List<Destination> queryByRidSon(Long rid) {
        LambdaQueryWrapper<Destination> wrapper = new LambdaQueryWrapper<>();
        List<Destination> list = new ArrayList<>();
        if (rid == -1) {
            wrapper.eq(Destination::getParentId,1);
            list = super.list(wrapper);
        } else {
            list = this.viewDestination(rid);
        }
        for (Destination destination : list) {
            wrapper.clear();
            wrapper.eq(Destination::getParentId,destination.getId());
            wrapper.last("limit 5");
            List<Destination> children = super.list(wrapper);
            destination.setChildren(children);
        }
        return list;
    }
}
