package article.models;

import java.util.List;

import article.controllers.PageNation;

public interface ArticleDAO {

	void insertArticle(ArticleVO articleVO) throws Exception;
	ArticleVO getDetail(long no) throws Exception;
	void updateViewcount(long no) throws Exception;
	void updateAction(ArticleVO articleVO) throws Exception;
	void deleteAction(ArticleVO articleVO) throws Exception;
	
	List<ArticleVO> getArticleList() throws Exception;
	List<ArticleVO> getArticleList(PageVO pageVO) throws Exception;
	
	long gettotalCount() throws Exception;
	List<ArticleVO> getArticleList(PageNation pageNation) throws Exception;
}
	