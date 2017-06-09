//ハッシュ法のチェイン法によるデータ構造の実現
//　2017/06/09 (c)tane
import java.util.*;

public class hashchain
{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		HashChain hc = new HashChain();
		hc.insert(111,"たねちゃん111");
		hc.insert(3,"たねちゃん3");
		hc.insert(19,"たねちゃん19");
		hc.insert(555,"たねちゃん555");
		System.out.println("****ハッシュ値の確認****");
		System.out.println("111:"+hc.hash(111));
		System.out.println("3:"+hc.hash(3));
		System.out.println("19:"+hc.hash(19));
		System.out.println("555:"+hc.hash(555));
		System.out.println("****値の検索*****");
		System.out.println("111:"+hc.find(111));
		System.out.println("3:"+hc.find(3));
		System.out.println("19:"+hc.find(19));
		System.out.println("存在しないキー:"+hc.find(5));
		System.out.println("555:"+hc.find(555));
		System.out.println("****全データ出力*****");
		hc.printAll();
		hc.delete(5);
		hc.delete(3);
		hc.delete(555);
		hc.delete(111);
		System.out.println("****データ削除後のデータ出力*****");
		hc.printAll();
	}
	
	public static class HashCell
	{
		private int key;
		private String value;
		private HashCell next;
		
		HashCell(int key, String value){
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		public void setKey(int key){this.key=key;}
		public int getKey(){return this.key;}
		public void setValue(String v){this.value=value;}
		public String getValue(){return this.value;}
		public void setNext(HashCell n){this.next=n;}
		public HashCell getNext(){return this.next;}
	}
	
	public static class HashChain
	{
		HashCell[] hashTable = new HashCell[10];
		
		HashChain(){
			for(int i=0;i<10;i++){
				hashTable[i] = null;
			}
		}
		
		public int hash(int key){
		//keyの各桁を足しあわせて１桁の数値にしたものをハッシュキーとする
			int ans = key/10 + key%10;
			if (ans>=10) ans=hash(ans);
			return ans;
		}
		
		public String find(int key){
			int h = hash(key);
			for(HashCell cell=this.hashTable[h];cell != null;cell=cell.getNext()){
				if(cell.getKey() == key) return cell.getValue();
			}
			return null;
		}
		
		public boolean insert(int key, String value){
			if(find(key)!=null){
				//すでにこのキーでデータが存在するのでエラー
				return false;
			}
		
			int h = hash(key);
			HashCell newcell = new HashCell(key, value);
			
			if(this.hashTable[h]==null){
				this.hashTable[h] = newcell;
				return true;
			}
			
			newcell.setNext(this.hashTable[h].getNext());
			this.hashTable[h].setNext(newcell);
			return true;
		}
		
		public boolean delete(int key){
			int h = hash(key);
			HashCell precell = this.hashTable[h];
			
			if(precell==null) {
				return false;
			}
			
			if(precell.getKey()==key){
				if(precell.getNext()==null){
					this.hashTable[h] = null;
					return true;
				} else {
					this.hashTable[h] = precell.getNext();
					return true;
				}
			}
			
			for(HashCell cell=precell.getNext();cell!=null;cell=cell.getNext()){
				if(cell.getKey()==key){
					precell.setNext(cell.getNext());
					return true;
				}
				precell=cell;
			}
			
			return false;
		}
		
		public void printAll(){
			for(int i=0;i<10;i++){
				HashCell cell=this.hashTable[i];
				while(cell!=null){
					System.out.println(cell.getKey()+":"+cell.getValue());
					cell=cell.getNext();
				}
			}
		}
	}
}
