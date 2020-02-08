package cn.snoopy.community.service;

import cn.snoopy.community.dto.NotifyDTO;
import cn.snoopy.community.dto.PaginationDTO;
import cn.snoopy.community.enums.NotifyStatusEnum;
import cn.snoopy.community.enums.NotifyTypeEnum;
import cn.snoopy.community.exception.CustomizeErrorCode;
import cn.snoopy.community.exception.CustomizeException;
import cn.snoopy.community.mapper.NotifyMapper;
import cn.snoopy.community.model.Notify;
import cn.snoopy.community.model.NotifyExample;
import cn.snoopy.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotifyService {
    @Autowired
    private NotifyMapper notifyMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotifyDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notifyMapper.countByExample(notifyExample);
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount /size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page-1);
        NotifyExample example = new NotifyExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notify> notifies = notifyMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if(notifies.size() == 0){
            return paginationDTO;
        }
        List<NotifyDTO> notifyDTOList = new ArrayList<>();

        for (Notify notify : notifies) {
            NotifyDTO notifyDTO = new NotifyDTO();
            BeanUtils.copyProperties(notify,notifyDTO);
            notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
            notifyDTOList.add(notifyDTO);
        }
        paginationDTO.setData(notifyDTOList);
        return paginationDTO;
    }


    public Long unreadCount(Long userId) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andReceiverEqualTo(userId)
                .andStatusEqualTo(NotifyStatusEnum.UMREAD.getStatus());
        return notifyMapper.countByExample(notifyExample);

    }

    public NotifyDTO read(Long id, User user) {
        Notify notify = notifyMapper.selectByPrimaryKey(id);
        if(notify == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFY_NOT_FOUND);
        }
        if(!Objects.equals(notify.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_RECEIVER_FAIL);
        }
        notify.setStatus(NotifyStatusEnum.READ.getStatus());
        notifyMapper.updateByPrimaryKey(notify);

        NotifyDTO notifyDTO = new NotifyDTO();
        BeanUtils.copyProperties(notify,notifyDTO);
        notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
        return notifyDTO;
    }
}
