package com.kosmo.ormtest;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.kosmo.board.BoardVO;

public class DBCPCallTestForMyBatis {

	public static void main(String[] args) {
		DBCPCallTestForMyBatis db = new DBCPCallTestForMyBatis();
		SqlSession conn = db.dbConn();
		db.dbClose(conn);
	}

	public void dbClose(SqlSession conn) {
		if(conn != null) {
			System.out.println("conn close");
			conn.close();
		}
	}

	//public Connection dbConn() {
	public SqlSession dbConn() {
		Reader reader = null;
		SqlSessionFactory dbFactory = null;
		SqlSession conn = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-context.xml");
			dbFactory = new SqlSessionFactoryBuilder().build(reader);

			if(dbFactory == null)
				System.out.println("factroy null");
			else
				System.out.println("factroy open");

			conn = dbFactory.openSession();
			if(conn == null)
				System.out.println("conn null");
			else
				System.out.println("conn open");


			List<BoardVO> list
			= conn.selectList("boardNameSpace.list");
			System.out.println(list.size() + ":" + list.get(0).getBtitle());


			BoardVO vo = new BoardVO();
			vo.setBtitle("title33333333");
			int res = conn.insert("boardNameSpace.boardInsertID", vo);
			conn.commit();


			System.out.println(res);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
