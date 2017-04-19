package yyl.example.demo.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDbJDbcTest {

	public static void main(String[] args) {
		try (MongoClient client = new MongoClient("localhost", 27017)) {
			MongoDatabase database = client.getDatabase("my-test-db");

			//获取集合
			MongoCollection<Document> collection = database.getCollection("test-collection");

			//删除集合
			collection.drop();

			//插入文档
			for (int i = 0; i < 3; i++) {
				Document document = new Document();
				document.put("_id", i);
				document.put("name", "测试" + i);
				collection.insertOne(document);
			}

			//更新文档
			{
				Document document = new Document();
				document.put("name", "测试零");
				collection.updateMany(Filters.eq("_id", 0), new Document("$set", document));
			}
			
			System.out.println(collection.count());
			
			//删除文档
			collection.deleteOne(Filters.eq("_id", 2));

			//检索文档
			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

			System.out.println(collection.count());
			
			//删除库
			database.drop();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}
