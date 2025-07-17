// public class HeroRepository {
//     public static void insertHero(String name, String heroClass, int level) {
//         String sql = "INSERT INTO heroes(name, heroClass, level) VALUES(?, ?, ?)";

//         try (Connection conn = DatabaseManager.connect();
//              var pstmt = conn.prepareStatement(sql)) {

//             pstmt.setString(1, name);
//             pstmt.setString(2, heroClass);
//             pstmt.setInt(3, level);

//             pstmt.executeUpdate();
//             System.out.println("✅ Hero inserted.");
//         } catch (SQLException e) {
//             System.out.println("❌ Insertion failed: " + e.getMessage());
//         }
//     }
// }
// public class HeroRepository {
//     public static void getAllHeroes() {
//         String sql = "SELECT * FROM heroes";

//         try (Connection conn = DatabaseManager.connect();
//              var stmt = conn.createStatement();
//              var rs = stmt.executeQuery(sql)) {

//             while (rs.next()) {
//                 System.out.println(
//                     rs.getInt("id") + ": " +
//                     rs.getString("name") + " - " +
//                     rs.getString("heroClass") + " (lvl " +
//                     rs.getInt("level") + ")"
//                 );
//             }
//         } catch (SQLException e) {
//             System.out.println("❌ Error fetching heroes: " + e.getMessage());
//         }
//     }
// }
