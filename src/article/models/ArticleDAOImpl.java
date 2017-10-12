package article.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.ibatis.sqlmap.client.SqlMapClient;

import article.controllers.PageNation;
import ibatis.QueryHandler;

public class ArticleDAOImpl implements ArticleDAO {
	private static ArticleDAOImpl articleDAO = null;
/*
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;*/
	
	private DataSource ds = null; 

	private ArticleDAOImpl() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context)context.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/mydbcp");
		} catch (Exception e) {
			e.printStackTrace();
		}
/*		Properties pr = new Properties();
		String props = this.getClass().getResource("").getPath() + "/database.properties";
		try {
			pr.load(new FileInputStream(props));

			driver = pr.getProperty("driver");
			url = pr.getProperty("url");
			username = pr.getProperty("username");
			password = pr.getProperty("password");

			Class.forName(driver);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}*/
	}

	private Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static ArticleDAOImpl getInstance() {
		if (articleDAO == null) {
			articleDAO = new ArticleDAOImpl();
		}
		return articleDAO;
	}

	@SuppressWarnings("unused")
	private void dbClose(PreparedStatement ps, Connection cn) {
		if (ps != null)
			try {
				ps.close();
			} catch (Exception e) {
			}
		if (cn != null)
			try {
				cn.close();
			} catch (Exception e) {
			}
	}

	private void dbClose(ResultSet rs, PreparedStatement ps, Connection cn) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
		if (ps != null)
			try {
				ps.close();
			} catch (Exception e) {
			}
		if (cn != null)
			try {
				cn.close();
			} catch (Exception e) {
			}
	}

	@Override
	public void insertArticle(ArticleVO articleVO) throws Exception {
		SqlMapClient sqlMap =  QueryHandler.getInstance();
		sqlMap.insert("article.insertArticle", articleVO);
/*		Connection cn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO tb_article(no, title, name, pwd, content)");
		sql.append(" VALUES(seq_article.nextval, ?, ?, ?, ?)");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());

			ps.setString(1, articleVO.getTitle());
			ps.setString(2, articleVO.getName());
			ps.setString(3, articleVO.getPwd());
			ps.setString(4, articleVO.getContent());
			ps.executeUpdate();
		} finally {
			dbClose(ps, cn);
		}*/
	}

	@Override
	public List<ArticleVO> getArticleList() throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ArticleVO> list = new ArrayList<ArticleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT no, title, name, regdate, viewcount");
		sql.append(" FROM   tb_article");
		sql.append(" ORDER  BY no DESC");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				ArticleVO articleVO = new ArticleVO();
				articleVO.setNo(rs.getLong("no"));
				articleVO.setTitle(rs.getString("title"));
				articleVO.setName(rs.getString("name"));
				articleVO.setRegdate(rs.getDate("regdate"));
				articleVO.setViewcount(rs.getInt("viewcount"));
				list.add(articleVO);
			}
		} finally {
			dbClose(rs, ps, cn);
		}
		return list;
	}

	@Override
	public List<ArticleVO> getArticleList(PageVO pageVO) throws Exception {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ArticleVO> list = new ArrayList<ArticleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT B.*");
		sql.append(" FROM (SELECT rownum AS rnum, A.*");
		sql.append(" 	  FROM (SELECT no, title, name, regdate, viewcount");
		sql.append(" 	        FROM   tb_article");
		sql.append(" 	        ORDER  BY no DESC) A) B");
		sql.append(" WHERE rnum between ? and ?");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());
			ps.setLong(1, pageVO.getStartnum());
			ps.setLong(2, pageVO.getEndnum());
			rs = ps.executeQuery();
			while (rs.next()) {
				ArticleVO articleVO = new ArticleVO();
				articleVO.setNo(rs.getLong("no"));
				articleVO.setTitle(rs.getString("title"));
				articleVO.setName(rs.getString("name"));
				articleVO.setRegdate(rs.getDate("regdate"));
				articleVO.setViewcount(rs.getInt("viewcount"));
				list.add(articleVO);
			}
		} finally {
			dbClose(rs, ps, cn);
		}
		return list;
	}

	@Override
	public ArticleVO getDetail(long no) throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		return (ArticleVO) sqlMap.queryForObject("article.getDetail",no);
/*		ArticleVO articleVO = null;

		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT no, title, name, regdate, viewcount, content");
		sql.append(" FROM   tb_article");
		sql.append(" WHERE  no=?");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());
			ps.setLong(1, no);
			rs = ps.executeQuery();
			if (rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setNo(rs.getLong("no"));
				articleVO.setTitle(rs.getString("title"));
				articleVO.setName(rs.getString("name"));
				articleVO.setRegdate(rs.getDate("regdate"));
				articleVO.setViewcount(rs.getInt("viewcount"));
				articleVO.setContent(rs.getString("content"));
			} else {
				throw new RuntimeException("잘못된 접근이거나 해당 게시물이 삭제되었습니다.");
			}
		} finally {
			dbClose(rs, ps, cn);
		}
		return articleVO;*/
	}

	@Override
	public void updateViewcount(long no) throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		sqlMap.update("article.updateViewcount", no);
		/*Connection cn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" update tb_article set");
		sql.append("        viewcount = viewcount + 1");
		sql.append(" where  no = ?");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());
			ps.setLong(1, no);
			if (ps.executeUpdate() == 0) {
				throw new RuntimeException(no + "번 게시물이 존재하지 않습니다.");
			}
		} finally {
			dbClose(ps, cn);
		}*/
	}

	@Override
	public void updateAction(ArticleVO articleVO) throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		
		if (sqlMap.update("article.updateAction", articleVO) == 0){
			throw new RuntimeException("비밀번호가 틀립니다.");
		}
		/*Connection cn = null;
		PreparedStatement ps = null;
		// resultset select 할때
		StringBuffer sql = new StringBuffer();

		sql.append(" UPDATE tb_article SET");
		sql.append("         name=?");
		sql.append("        ,title=?");
		sql.append("        ,content=?");
		sql.append(" WHERE no=? AND pwd=?"); // no 와 pwd 가 일치

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());

			ps.setString(1, articleVO.getName());
			ps.setString(2, articleVO.getTitle());
			ps.setString(3, articleVO.getContent());
			ps.setLong(4, articleVO.getNo());
			ps.setString(5, articleVO.getPwd());
			if (ps.executeUpdate() == 0) {
				throw new RuntimeException("비밀번호가 틀립니다."); // 변경시 비밀번호 작동 수정시
			}
		} finally {
			dbClose(ps, cn);
		}*/
	}

	@Override
	public void deleteAction(ArticleVO articleVO) throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		
		if (sqlMap.delete("article.deleteAction", articleVO) == 0){
			throw new RuntimeException("비밀번호가 틀립니다.");
		}
		/*Connection cn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM tb_article");
		sql.append(" WHERE no=? AND pwd=?"); //

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());

			ps.setLong(1, articleVO.getNo());
			ps.setString(2, articleVO.getPwd());
			if (ps.executeUpdate() == 0) {
				throw new RuntimeException("비밀번호가 틀립니다."); // 변경시 비밀번호 작동 수정시
			}
		} finally {
			dbClose(ps, cn);
		}*/
		
	}

	@Override
	public long gettotalCount() throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		return (Long) sqlMap.queryForObject("article.gettotalCount");
		
		
/*		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql_totalcount = "select count(*) as cnt from tb_article";
		long result = 0;

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql_totalcount);
			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getLong("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose(rs, ps, cn);
		}
		return result;*/
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleVO> getArticleList(PageNation pageNation) throws Exception {
		SqlMapClient sqlMap = QueryHandler.getInstance();
		return sqlMap.queryForList("article.getArticleList", pageNation);		
		/*Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ArticleVO> list = new ArrayList<ArticleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT B.*");
		sql.append(" FROM (SELECT rownum AS rnum, A.*");
		sql.append(" 	  FROM (SELECT no, title, name, regdate, viewcount");
		sql.append(" 	        FROM   tb_article");
		sql.append(" 	        ORDER  BY no DESC) A) B");
		sql.append(" WHERE rnum between ? and ?");

		try {
			cn = getConnection();
			ps = cn.prepareStatement(sql.toString());
			ps.setLong(1, pageNation.getStartnum() );
			ps.setLong(2, pageNation.getEndnum());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ArticleVO articleVO = new ArticleVO();
				articleVO.setNo(rs.getLong("no"));
				articleVO.setTitle(rs.getString("title"));
				articleVO.setName(rs.getString("name"));
				articleVO.setRegdate(rs.getDate("regdate"));
				articleVO.setViewcount(rs.getInt("viewcount"));
				list.add(articleVO);
			}
		} finally {
			dbClose(rs, ps, cn);
		}
		return list;*/
	}
}
