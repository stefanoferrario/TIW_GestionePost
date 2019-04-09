package dao;

import beans.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PostDAO {
    private Connection _connection;

    public PostDAO(Connection connection) {
        _connection = connection;
    }

    public List<Post> findAllSorted() throws SQLException {
        List<Post> posts = new ArrayList<Post>();
        String query = "SELECT * FROM posts";
        ResultSet result = null;
        PreparedStatement pstatement = null;
        try {
            pstatement = _connection.prepareStatement(query);
            result = pstatement.executeQuery();
            String pUser, pText, pImg;
            int pLikes, pID;
            Date pDate;
            while (result.next()) {
                pID = result.getInt(1);
                pText = result.getString(2);
                pDate = result.getDate(3);
                pImg = result.getString(4);
                pLikes = result.getInt(5);
                pUser = result.getString(6);
                posts.add(new Post(pID, pText, pDate, pUser, pImg, pLikes));
            }

        } catch (SQLException e) {
            throw new SQLException(e);

        } finally {
            try {
                result.close();
                pstatement.close();
            } catch (SQLException e1) {
                throw new SQLException("Cannot close");
            }
        }

        //vanno ordinati per data ma non ricordo se crescente o decrescente
        Collections.sort(posts, Collections.reverseOrder());
        //Collections.sort(posts);
        return posts;
    }

    public void createPost(Post p) throws SQLException {
        String query = "INSERT INTO Posts(text, date, likes, userId, image) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstatement = null;

        pstatement = _connection.prepareStatement(query);

        pstatement.setString(1, p.getText());
        pstatement.setDate(2, new java.sql.Date(p.getDate().getTime()));
        pstatement.setInt(3, p.getLikes());
        pstatement.setString(4, p.getUserid());
        pstatement.setString(5,  p.getImage());
        pstatement.executeUpdate();

        try {
            pstatement.close();
        } catch (SQLException e1) {
            throw new SQLException("Cannot close");
        }

    }
}

