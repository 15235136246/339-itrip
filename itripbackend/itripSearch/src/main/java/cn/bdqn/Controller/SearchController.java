package cn.bdqn.Controller;

import cn.bdqn.dao.BaseSolr;
import cn.bdqn.entity.ItripHotelVO;
import cn.bdqn.entity.Page;
import cn.bdqn.entity.SearchHotCityVO;
import cn.bdqn.entity.SearchHotelVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.dto.Dto;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class SearchController {
    @RequestMapping(value="/api/hotellist/searchItripHotelListByHotCity",method=RequestMethod.POST,produces= "application/json")
    @ResponseBody
    public Dto GetList(@RequestBody SearchHotCityVO searchHotCityVO) throws IOException, SolrServerException {
        BaseSolr baseSolr=new BaseSolr();
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.addFilterQuery("cityId:"+searchHotCityVO.getCityId());
        return DtoUtil.returnDataSuccess(baseSolr.GetList(solrQuery));

    }

    @RequestMapping(value="/api/hotellist/searchItripHotelPage",method=RequestMethod.POST,produces= "application/json")
    @ResponseBody
    public Dto<Page<ItripHotelVO>> GetList(@RequestBody SearchHotelVO vo) throws Exception {

        SolrQuery solrQuery=new SolrQuery();

        BaseSolr baseSolr=new BaseSolr();

        if (vo.getKeywords()!=null){

            solrQuery.addFilterQuery("keyword:"+vo.getKeywords());
        }
        if (vo.getDestination()!=null){
            solrQuery.addFilterQuery("destination:"+vo.getDestination());
        }
        if (vo.getHotelLevel()!=null){
            solrQuery.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
        }
        if (vo.getMinPrice()!=null){
            solrQuery.addFilterQuery("minPrice:["+vo.getMinPrice()+" TO *]");
        }
        if (vo.getMaxPrice()!=null){
            solrQuery.addFilterQuery("minPrice:[* TO "+vo.getMaxPrice()+"]");
        }
        //特色
        if (vo.getFeatureIds()!=null){
            String [] list= vo.getFeatureIds().split(",");
            for (int i =0;i<list.length;i++){
                if (i==0) {
                    solrQuery.addFilterQuery("featureIds:*" + list[i] + "*");
                }else {
                    solrQuery.addFilterQuery(" or featureIds:*" + list[i] + "*");
                }
            }
        }
        //商圈
        if (vo.getTradeAreaIds()!=null){
            String [] list= vo.getTradeAreaIds().split(",");
            for (int i =0;i<list.length;i++){
                if (i==0) {
                    solrQuery.addFilterQuery("tradingAreaIds:*" + list[i] + "*");
                }else {
                    solrQuery.addFilterQuery(" or tradingAreaIds:*" + list[i] + "*");
                }
            }
        }


        if (vo.getPageNo()==null){
            vo.setPageNo(1);
        }
        if (vo.getPageSize()==null){
            vo.setPageSize(6);
        }
        Page<ItripHotelVO> page= baseSolr.GetlistBypage(solrQuery,vo.getPageNo(),vo.getPageSize());

        return DtoUtil.returnDataSuccess(page);

    }
}
