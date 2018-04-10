package com.kosmo.ormtest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class DBCPCallTestForSpring {

	public static void main(String[] args) {

//		GenericXmlApplicationContext ctx
//		 = new GenericXmlApplicationContext("classpath:mybatis-context.xml");
//		DataSource dao = (DataSource)ctx.getBean("kosmoDS");
//		if(conn == null)
//			System.out.println("conn null");
//		else
//			System.out.println("conn ok");

		Resource resource = new FileSystemResource("D:\\workspace_spring\\SPRING_ORM\\src\\main\\webapp\\WEB-INF\\servlet-context.xml");
		BeanFactory bf = new XmlBeanFactory(resource);

		DataSource ds = (DataSource)bf.getBean("kosmoDS");
		Connection conn = null;
		try {
			conn = ds.getConnection();
			if(conn == null)
				System.out.println("conn null");
			else
				System.out.println("conn ok");


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int cnt = 0;
			String sql = "select count(*) cnt from board";
			//conn = db.dbConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt  = rs.getInt("cnt");
			}
			System.out.println(cnt+"건 출력");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("conn close");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}




	}

}
