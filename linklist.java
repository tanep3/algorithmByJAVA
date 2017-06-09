//単純連結リストによるデータ構造
//データ挿入時に自動的に昇順ソートされます。
// 2017/06/06 (c)tane.

import java.util.*;

public class linklist
{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		Mylist ml = new Mylist();
		ml.insert("tane");
		ml.insert("take");
		ml.insert("hana");
		System.out.println(ml.insert("4kenme"));
		System.out.println(ml.insert("toko"));
		
		ml.print();
		
		System.out.println(ml.delete("take"));
		System.out.println(ml.delete("oira"));
		ml.print();
		System.out.println(ml.length());
		
	}
	
	static class Cell{
		private Cell nextlink;
		private String data;
	
		Cell(String str){
			this.data = str;
			this.nextlink = null;
		}
		
		String getData(){return this.data;}
		Cell getNextlink(){return this.nextlink;}
		void setData(String d){this.data=d;}
		void setNextlink(Cell l){this.nextlink=l;}
		
	}
	
	static class Mylist{
		private final Cell head;
		private int size;
		
		Mylist(){
			this.head = new Cell("Head cell");
			this.size = 0;
		}
		
		public int insert(String str){
			Cell newcell = new Cell(str);
			
			Cell precell = this.head;
			Cell cell = this.head.getNextlink();
			int num = 0;
			
			while(cell!=null){
				if(cell.data.compareTo(str)>0) break;
				precell = cell;
				cell = cell.getNextlink();
				num++;
			}
			
			precell.setNextlink(newcell);
			newcell.setNextlink(cell);
			this.size++;
			
			return num;
		}
		
		public int delete(String str){
			Cell precell = this.head;
			Cell cell = this.head.getNextlink();
			
			while(cell!=null){
				
				if(str.compareTo(cell.getData())==0){
					
					precell.setNextlink(cell.getNextlink());
					this.size--;
					return this.size;
				}
				precell = cell;
				cell = cell.getNextlink();
			}
			return -1;
		}
		
		public int length(){
			return this.size;
		}
		
		public void print(){
			Cell nowcell = this.head.getNextlink();
			int i=0;
			
			while(nowcell!=null){
				System.out.println(i+":"+nowcell.getData());
				nowcell=nowcell.getNextlink();
				i++;
			}
		}
	}
	
}
