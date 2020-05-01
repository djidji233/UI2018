package model;

import java.util.List;
import java.util.Map;

public class IzmenaItem implements Comparable<IzmenaItem>{
	
	public static final int IZMENA = 0, BRISANJE = 1, DODAVANJE = 2;
	
	
	private Entry entry;
	private int tip;
	
	public IzmenaItem(Entry entry, int tip) {
		super();
		this.entry = entry;
		this.tip = tip;
	}
	
	public IzmenaItem(Entity entitet, String line) {
		
		
		int k=0;
		
		this.entry = new Entry(entitet);
		List<Attribute> atributi = entry.getEntitet().getAttributes();
		
		Map<Attribute, String> fields = this.entry.getFields();

		for(int i=0;i<atributi.size();i++) {
						
			int len =atributi.get(i).getLength();
			String s1 = line.substring(k, k+len).trim();
			
			fields.put(atributi.get(i), s1);
			
			k+=len;
			
		}
		tip = line.charAt(k)-'0';
		
	}

	@Override
	public String toString() {
		
		String s = "";
		Entity e = entry.getEntitet();
		
		int n = e.getAttributes().size();
		
		for(int i =0;i<n;i++) {
			
			Attribute atr = e.getAttributes().get(i);
			
			String tmp = entry.getFields().get(atr);
			s += tmp;
			
			for(int j=tmp.length();j<atr.getLength();j++)
				s+=" ";
			
			
		}
		s += tip+"\r\n"; 
		
		return s;
	}

	public Entry getEntry() {
		return entry;
	}

	public int getTip() {
		return tip;
	}

	@Override
	public int compareTo(IzmenaItem item) {
		
		List<Attribute> atributi = entry.getEntitet().getAttributes();
		for(int i=0;i<atributi.size();i++)
		{
			if(atributi.get(i).isPrimaryKey()) {
				String s1 = entry.getFields().get(atributi.get(i)).toLowerCase();
				String s2 = item.getEntry().getFields().get(atributi.get(i)).toLowerCase();
	
				
				if (!s1.equals(s2))
					if (atributi.get(i).getType().equals(AttributeType.TYPE_NUMERIC)) {
						return Integer.parseInt(s1) - Integer.parseInt(s2);
					} else
						return s1.compareTo(s2);
				
			}
			else
				return 0;
		}
		
		return 0;
	}
	
	
	
	
}
