package com.abdool.daos;

import com.abdool.Scores;
import com.abdool.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO implements GenericDAO<Scores> {

    private static ConnectionUtil cu =ConnectionUtil.getConnectionUtil();

    @Override
    public Scores add(Scores scores) {
        String sql = "insert into scores values (default, ? , ?) returning *";

        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, scores.getInitials());
            ps.setInt(2,scores.getScore());

            ResultSet rs = ps.executeQuery();

            //expecting only 1 query to return
            if(rs.next()) {
                return new Scores(
                        rs.getInt("id"),
                        rs.getString("initials"),
                        rs.getInt("score")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Scores getById(int id) {
        String sql = "select * from scores where id = ?";

        try (Connection conn = cu.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);

            //parameters in this instance are indexed starting at 1
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            //expecting only 1 query to return
            if(rs.next()) {
                return new Scores(
                        rs.getInt("id"),
                        rs.getString("initials"),
                        rs.getInt("score")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Scores> getAll() {
        //first - we write our SQL as a string to get scores
        String sql = "select * from scores";

        //we will also need a list to store the results
        List<Scores> scores = new ArrayList<>();

        //next - establish a connection to the database using our ConnectionUtil
        try (Connection conn = cu.getConnection()) {
            //now that we have a connection - we can build our query/statement
            PreparedStatement ps = conn.prepareStatement(sql);

            // then we execute the statement -> sending it to our db server and receiving the results
            ResultSet rs = ps.executeQuery();

            // now that we have our results - we can iterate over that table (ResultSet)
            while(rs.next()) {
                //for each row, we'll create a Java Object
                scores.add(new Scores(
                        rs.getInt("id"),
                        rs.getString("initials"),   //make sure they are exactly like DBeaver.
                        rs.getInt("score")
                    )
                );
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Scores scores) {
        String sql = "update scores set initials = ?, score = ? where id = ?";

        try(Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, scores.getInitials());
            ps.setInt(2, scores.getScore());
            ps.setInt(3, scores.getId());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from scores where id = ?";

        try (Connection conn = cu.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
