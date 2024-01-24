package kr.or.ddit.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory;
	public static InputStream in;
	
	static {
		try {
			in = Resources.getResourceAsStream(
					"kr/or/ddit/mybatis/config/mybatis-config.xml");
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
			
		} catch (Exception e) {
			System.out.println("MyBatis 초기화 실패");
			e.printStackTrace();
		}finally {
			if(in!=null)try {in.close();} catch (Exception e) {e.printStackTrace();}
		}
	}//static 초기화 블럭 끝
	
	//sqlSession객체를 반환하는 메서드
	public static SqlSession getSqlSession() {
		SqlSession session = sqlSessionFactory.openSession();
		return session;
	}
}
