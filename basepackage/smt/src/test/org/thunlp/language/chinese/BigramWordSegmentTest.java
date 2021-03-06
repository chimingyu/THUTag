package org.thunlp.language.chinese;

import java.io.IOException;

import org.thunlp.io.TextFileReader;

import junit.framework.Assert;
import junit.framework.TestCase;

public class BigramWordSegmentTest extends TestCase {
	public void testBasic() {
		BigramWordSegment ws = new BigramWordSegment();
		String input = "我们是中国人, 是不善于speak English的~至少有时候,哈,是的";
		String[] standard = { "我们", "们是", "是中", "中国", "国人", ",", "是不", "不善", "善于", "speak", "English", "的", "~", "至少",
				"少有", "有时", "时候", ",", "哈", ",", "是的" };

		String[] output = ws.segment(input);
		Assert.assertEquals(standard.length, output.length);
		for (int i = 0; i < output.length; i++) {
			// System.out.println(output[i]);
			Assert.assertEquals(standard[i], output[i]);
		}
	}

	public void testIndexOutofRange() throws IOException {
		String content = TextFileReader.readAll("./src/test/org/thunlp/language/chinese/TestIndexOutofRange.txt",
				"GB18030");
		BigramWordSegment ws = new BigramWordSegment();
		ws.segment(content);
	}
}
