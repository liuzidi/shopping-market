package com.lzd.service.impl;

import com.lzd.dao.IndexImgMapper;
import com.lzd.entity.IndexImg;
import com.lzd.service.IndexImgService;
import com.lzd.vo.CodeStatus;
import com.lzd.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:liuzidi
 * @Description:
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Autowired
    private IndexImgMapper indexImgMapper;

    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if(indexImgs.size()==0){
            return new ResultVO(CodeStatus.FAILED,"fail",null);
        }else{
            return new ResultVO(CodeStatus.OK,"success",indexImgs);
        }
    }
}
