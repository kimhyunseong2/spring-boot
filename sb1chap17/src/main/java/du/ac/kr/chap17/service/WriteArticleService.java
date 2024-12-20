package du.ac.kr.chap17.service;

import du.ac.kr.chap17.dao.ArticleDao;
import du.ac.kr.chap17.dao.IdGenerator;
import du.ac.kr.chap17.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;

@Service
public class WriteArticleService {
	@Autowired
	IdGenerator idGenerator;

	@Autowired
	ArticleDao articleDao;

	public Article write(WritingRequest writingRequest)
			throws IdGenerationFailedException {

		int groupId = idGenerator.generateNextId("article");

		Article article = writingRequest.toArticle();

		article.setGroupId(groupId);
		article.setPostingDate(new Date());
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		article.setSequenceNumber(decimalFormat.format(groupId) + "999999");

			int articleId = articleDao.insert(article);


			article.setId(articleId);
			return article;

	}
}
