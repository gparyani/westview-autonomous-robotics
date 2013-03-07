package year2013;

import java.util.ArrayList;
import java.util.Collections;

public class Mapping<K extends Comparable<K>, V>
{
	private SortedList<Entry<K, V>> entries;
	
	public Mapping()
	{
		entries = new SortedList<Entry<K, V>>();
	}
	
	private int indexOf(K key)
	{
		return entries.indexOf(new Entry<K, V>(key, null));
	}
	
	public void add(K key, V value)
	{
		entries.add(new Entry<K, V>(key, value));
	}
	public void remove(K key)
	{
		entries.remove(indexOf(key));
	}
	
	public Entry<K, V> lower(K key)
	{
		for (int i = entries.size() - 1; i >= 0; i--)
		{
			Entry<K, V> entry = entries.get(i);
			if (entry.key.compareTo(key) < 0)
				return entry;
		}
		return null;
	}
	public Entry<K, V> higher(K key)
	{
		for (int i = 0; i < entries.size(); i++)
		{
			Entry<K, V> entry = entries.get(i);
			if (entry.key.compareTo(key) > 0)
				return entry;
		}
		return null;
	}
	
	class SortedList<T> extends ArrayList<T>
	{
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
	    public boolean add(T value)
		{
	        boolean result = super.add(value);
	        Comparable<T> cmp = (Comparable<T>) value;
	        for (int i = size() - 1; i > 0 && cmp.compareTo(get(i - 1)) < 0; i--)
	            Collections.swap(this, i, i - 1);
	        return result;
	    }
	}
	
	public class Entry<K extends Comparable<K>, V>
	{
		public K key;
		public V value;
		
		public Entry(K key, V value)
		{
			this.key = key;
			this.value = value;
		}
		
		@SuppressWarnings("unchecked")
		public int compareTo(Object obj)
		{
			if (obj instanceof Entry)
				return key.compareTo(((Entry<K, V>)obj).key);
			else
				return key.compareTo((K)obj);
		}
		
		public boolean equals(Object obj)
		{
			return compareTo(obj) == 0;
		}
	}
}