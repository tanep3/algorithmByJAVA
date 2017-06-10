//Hash Open Address法によるデータ構造。
//ハッシュテーブルにデータを格納していき、衝突があったら
//再ハッシュして次の場所にデータを格納する。
// (c) 2017/06/10 tane.

public class hashopen
{
	public static void main(String[] args)
	{
		HashOpen ho = new HashOpen();
		
		System.out.println("****ハッシュ値の確認****");
		System.out.println(ho.hash("aa"));
		System.out.println(ho.hash("ab"));
		System.out.println(ho.hash("ac"));
		System.out.println(ho.hash("tane"));
		System.out.println(ho.hash("take"));
		System.out.println(ho.hash("pokl"));
		System.out.println(ho.hash("wow"));
		System.out.println(ho.hash("kyou"));
		System.out.println(ho.hash("ashita"));
		System.out.println(ho.hash("asatte"));
		System.out.println(ho.hash("gorou"));
		ho.insert("aa","ああ");
		ho.insert("ab","あべ");
		ho.insert("ac","あこ");
		ho.insert("tane","たね");
		ho.insert("take","たけ");
		ho.insert("pokl","ぽっくる");
		ho.insert("null","");
		ho.insert("wow","わお");
		ho.insert("kyou","今日");
		ho.insert("ashita","明日");
		ho.insert("asatte","明後日");
		ho.insert("gorou","五郎");
		ho.insert("null","一郎");
		System.out.println("****find****");
		System.out.println(ho.find("aa"));
		System.out.println(ho.find("ab"));
		System.out.println(ho.find("ac"));
		System.out.println(ho.find("tane"));
		System.out.println(ho.find("taneko"));
		System.out.println(ho.find("take"));
		System.out.println(ho.find("pokl"));
		System.out.println(ho.find("wow"));
		System.out.println(ho.find("kyou"));
		System.out.println(ho.find("ashita"));
		System.out.println(ho.find("asatte"));
		System.out.println(ho.find("gorou"));
		System.out.println("****print all****");
		ho.printAll();
		ho.delete("wani");
		ho.delete("aa");
		ho.delete("pokl");
		ho.delete("null");
		ho.delete("kyou");
		ho.delete("asatte");
		System.out.println("****削除後 print all****");
		ho.printAll();
		ho.insert("aa","もいちどああ");
		ho.insert("kyou","もいちど今日");
		System.out.println("****挿入後 print all****");
		ho.printAll();
	}
	
	public static class Bucket
	{
		//statusは 0:未使用 1:使用中 -1:削除済
		private int status;
		private String key;
		private String value;
		
		Bucket(){
			this.status=0;
			this.key=null;
			this.value=null;
		}
		
		public void setStatus(int s){this.status=s;}
		public void setKey(String k){this.key=k;}
		public void setValue(String v){this.value=v;}
		public int getStatus(){return this.status;}
		public String getKey(){return this.key;}
		public String getValue(){return this.value;}
	}
	
	public static class HashOpen
	{
		private final int bucket_size = 10;
		private Bucket[] bucket = new Bucket[10];
		
		HashOpen(){
			for(int i=0;i<10;i++){
				bucket[i] = new Bucket();
			}
		}
		
		public int hash(String key){
			int h=0;
			for(int i=0;i<key.length();i++){
				h += key.charAt(i);
			}
			while(h>=10){
				h = h/10 +h%10;
			}
			return h;
		}
		
		public int rehash(int h){
			return (h + 1) % this.bucket_size;
		}
		
		public String find(String key){
			int h = hash(key);
			Bucket b = this.bucket[h];
			int count = 1;
			while(b.getStatus()!=0){
				if(count++>bucket_size) {
					return null;
				}
				if(b.getStatus()==1 && key.equals(b.getKey())) return b.getValue();
				h=rehash(h);
				b=this.bucket[h];
			}
			 return null;
		}
		
		public boolean insert(String key, String value){
			//キーが存在してたらエラー
			if(find(key)!=null) {
				System.out.println("キー重複のため挿入できません。");
				return false;
			}
			
			int h = hash(key);
			Bucket b = this.bucket[h];
			int count = 1;
			while (b.getStatus()==1){
				if(count++>bucket_size) {
					System.out.println("データがいっぱいでもう格納できません。");
					return false;
				}
				h = rehash(h);
				b = this.bucket[h];
			}
			b.setStatus(1);
			b.setKey(key);
			b.setValue(value);
			return true;
		}
		
		public boolean delete(String key){
			int h = hash(key);
			Bucket b = this.bucket[h];
			int count=1;
			while(b.getStatus()!=0){
				if(count++>bucket_size) {
					System.out.println("一周まわして削除キーが見つかりませんでした。");
					return false;
				}
				if(b.getStatus()==1 && key.equals(b.getKey())){
					b.setStatus(-1);
					System.out.println(key+"を削除しました");
					return true;
				}
				h = rehash(h);
				b = this.bucket[h];
			}
			System.out.println("削除キーが見つかりませんでした。");
			return false;
		}
		
		public void printAll(){
			for(int i=0;i<10;i++){
				if(bucket[i].getStatus()==1){
					System.out.print(bucket[i].getKey()+":");
					System.out.println(bucket[i].getValue());
				}
			}
		}
	}
}
