package cn.service.impl;

import cn.entity.Kind;
import cn.mapper.KindMapper;
import cn.service.KindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
@Service
public class KindServiceImpl extends ServiceImpl<KindMapper, Kind> implements KindService {

}
