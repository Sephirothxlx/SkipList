package skiplist;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSkipList {

	@Test
	public void testSkipList() {
		SkipList skipList = new SkipList();
		
		skipList.put(5, "Five");
		skipList.put(0, "Zero");
		skipList.put(16, "Sixteen");
		skipList.put(4, "Four");
		skipList.put(7, "Seven");
		skipList.put(2, "Two");
		skipList.put(6, "Siix");
		skipList.put(6, "Six");
		skipList.put(11, "Eleven");
		skipList.put(1225, "Merry Christmas");
		
		assertEquals("Zero", skipList.get(0));
		assertEquals("Six", skipList.get(6));
		assertEquals("Eleven", skipList.get(11));
		assertEquals("Five", skipList.get(5));
		assertEquals("Merry Christmas", skipList.get(1225));
		
		assertEquals("Six", skipList.remove(6));
		assertEquals(null, skipList.get(6));
		
		System.out.println(skipList);
	}

}
