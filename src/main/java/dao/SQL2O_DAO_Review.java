package dao;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class SQL2O_DAO_Review implements DAO_Review {

    private final Sql2o sql2o;

    public SQL2O_DAO_Review(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (writtenBy, rating, content, restaurantId) VALUES (:writtenBy, :rating, :content, :restaurantId)";
        System.out.println(review);
        try (Connection con = sql2o.open()) {
            int idObject = (int) con.createQuery(sql)
                    .bind(review)
                    .addColumnMapping("restaurantId", "restaurantId")
                    .executeUpdate()
                    .getKey();
            review.setIdReview(idObject);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Review> getAllReviewsByRestaurant(int restaurantId) {
        String sql = "SELECT * FROM reviews WHERE restaurantId = :restaurantId";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("restaurantId", restaurantId)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public void deleteByIdReview(int idReview) {
        String sql = "DELETE from reviews WHERE idReview = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", idReview)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}