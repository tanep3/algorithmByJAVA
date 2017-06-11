//二分探索木の構造
//挿入、検索、削除、全件検索の機能を持つ
// (c) 2017/06/11 tane.

public class binaryserchtree
{
	public static void main(String[] args)
	{
		BinaryTree bt = new BinaryTree();
		
		bt.insert(5,"ごー");
		bt.insert(7,"なな");
		bt.insert(6,"ろく");
		bt.insert(2,"に");
		bt.insert(3,"さん");
		bt.insert(0,"ぜろ");
		bt.insert(8,"はち");
		bt.insert(9,"きゅう");
		bt.printAll();
		System.out.println("****find test****");
		System.out.println("5"+bt.find(5));
		System.out.println("7"+bt.find(7));
		System.out.println("6"+bt.find(6));
		System.out.println("2"+bt.find(2));
		System.out.println("1"+bt.find(1)+"<-見つからないケース");
		System.out.println("3"+bt.find(3));
		System.out.println("0"+bt.find(0));
		System.out.println("8"+bt.find(8));
		System.out.println("9"+bt.find(9));
		System.out.println("****削除後****");
		bt.delete(3);
		bt.delete(2);
		bt.delete(6);
		bt.delete(7);
		bt.insert(20,"にじゅう");
		bt.insert(10,"じゅう");
		bt.insert(30,"さんじゅう");
		System.out.println("3,2,6,7削除 20,10,30追加");
		bt.printAll();
		bt.delete(20);
		bt.delete(9);
		System.out.println("20、9削除");
		bt.printAll();
		bt.insert(9,"きゅうふたたび");
		bt.insert(15,"じゅうご");
		System.out.println("9、15追加");
		bt.printAll();
		bt.delete(10);
		System.out.println("10削除");
		bt.printAll();
		System.out.println("全件削除");
		bt.deleteAll();
		bt.printAll();
		bt.insert(50,"ごじゅう");
		bt.insert(30,"さんじゅう");
		bt.insert(80,"八十");
		bt.insert(10,"十");
		bt.insert(40,"四十");
		bt.insert(5,"ご");
		bt.insert(15,"十五");
		bt.insert(70,"七十");
		bt.insert(100,"百");
		bt.insert(60,"六十");
		bt.insert(75,"七十五");
		bt.insert(90,"九十");
		bt.insert(120,"百二十");
		bt.printAll();
		System.out.println("30,80削除");
		bt.delete(30);
		bt.delete(80);
		bt.printAll();
		bt.delete(50);
		bt.printAll();
		bt.delete(70);
		bt.delete(75);
		bt.delete(100);
		bt.delete(120);
		bt.delete(60);
		bt.printAll();
		bt.delete(5);
		bt.delete(15);
		bt.delete(90);
		bt.printAll();
	}
	
	public static class Node
	{
		//左右の子ノードへのポインタ
		public Node left;
		public Node right;
		
		//キーと値
		private int key;
		private String value;
		
		Node(int k, String v){
			this.left=null;
			this.right=null;
			setKey(k);
			setValue(v);
		}
		
		public void setKey(int k){this.key=k;}
		public void setValue(String v){this.value=v;}
		public int getKey(){return this.key;}
		public String getValue(){return this.value;}
	}
	
	public static class BinaryTree
	{
		private Node root; 
		
		BinaryTree(){
			root = null;
		}
		
		public String find(int key){
		//keyを検索し、値を返却する
		//keyが見つからなかったらnullを返却する
			String ans=null;
			Node node=findNodePointer(this.root,key);
			if(node==null) return null;
			return node.getValue();
		}
		
		private Node findNodePointer(Node node , int key){
			if(node==null) return null;
			Node ans=null;
			if(node.getKey()==key) ans=node;
			else if (node.getKey()>key) ans=findNodePointer(node.left,key);
			else if (node.getKey()<key) ans=findNodePointer(node.right,key);
			return ans;
		}
		
		public boolean insert(int key, String value){
		//二分木の適切なキーの位置に値を格納する
		//キーがすでに存在していたらfalseを返却する
			Node newnode = new Node(key,value);
			//もしルートが空ならそこに格納する
			if(this.root==null){
				this.root = newnode;
				return true;
			}
			
			Node node = this.root;
			Node prenode = this.root;
			boolean flgIsLeft=true;
			while(node!=null){
				if(node.getKey()==key){
					//キー重複でエラー
					return false;
				}else if(node.getKey()>key){
					//keyが小さいので左を検索
					prenode = node;
					node=node.left;
					flgIsLeft = true;
				}else if(node.getKey()<key){
					//keyが大きいので右を検索
					prenode = node;
					node=node.right;
					flgIsLeft = false;
				}
			}
			
			if(flgIsLeft){
				prenode.left = newnode;
			} else {
				prenode.right = newnode;
			}
			return true;
		}
		
		public void deleteAll(){
			this.root=null;
		}
		
		public boolean delete(int key){
		//キーのノードを削除する
		//キーが存在しなかったらfalseを返却する
			if (this.root==null) return false;
			
			Node deleteNode = this.root;
			//削除する位置を特定する
			if(this.root.getKey()==key){
				//rootの削除処理
				//子ノードが無いとき
				if(deleteNode.left==null && deleteNode.right==null){
					this.root=null;
					return true;
				}
				//子ノードが１つのとき
				if(deleteNode.left==null && deleteNode.right!=null){
					this.root=deleteNode.right;
					return true;
				}
				if(deleteNode.left!=null && deleteNode.right==null){
					this.root=deleteNode.left;
					return true;
				}
				//子ノードが２つのとき
				Node minNodeParent = findMinNodeParent(deleteNode.right);
				if(minNodeParent==null){
					//deleteNode.rightが親になるべきノード
					this.root=deleteNode.right;
					deleteNode.right.left=deleteNode.left;
					return true;
				}
				//minNodeをdeleteNodeの場所にもってくる
				Node minNode=minNodeParent.left;
				this.root=minNode;
				minNodeParent.left=minNode.right;
				minNode.right=deleteNode.right;
				minNode.left=deleteNode.left;
				return true;
				
			}
			
			Node deleteNodeParent = findNodeParent(key);
			if(deleteNodeParent==null) return false;
			//System.out.println("deleteParent="+deleteNodeParent.getKey());
			boolean flgIsLeft=true;
			if(deleteNodeParent.getKey()>key){
				flgIsLeft=true;
				deleteNode = deleteNodeParent.left;
			} else {
				flgIsLeft=false;
				deleteNode = deleteNodeParent.right;
			}
			//子ノードが無いとき
			if(deleteNode.left==null && deleteNode.right==null){
				if(flgIsLeft){
					deleteNodeParent.left=null;
				} else {
					deleteNodeParent.right=null;
				}
				return true;
			}
			//子ノードが１つのとき
			if(deleteNode.left==null && deleteNode.right!=null){
				if(flgIsLeft){
					deleteNodeParent.left=deleteNode.right;
				} else {
					deleteNodeParent.right=deleteNode.right;
				}
				return true;
			}
			if(deleteNode.left!=null && deleteNode.right==null){
				if(flgIsLeft){
					deleteNodeParent.left=deleteNode.left;
				} else {
					deleteNodeParent.right=deleteNode.left;
				}
				return true;
			}
			//子ノードが２つのとき
			Node minNodeParent = findMinNodeParent(deleteNode.right);
			if(minNodeParent==null){
				//deleteNode.leftが親になるべきノード
				if(flgIsLeft){
					deleteNodeParent.left=deleteNode.right;
				}else{
					deleteNodeParent.right=deleteNode.right;
				}
				deleteNode.right.left=deleteNode.left;
				return true;
			}
			//minNodeをdeleteNodeの場所にもってくる
			Node minNode=minNodeParent.left;
			if(flgIsLeft){
				deleteNodeParent.left=minNode;
			}else{
				deleteNodeParent.right=minNode;
			}
			minNodeParent.left=minNode.right;
			minNode.right=deleteNode.right;
			minNode.left=deleteNode.left;
			return true;
		}
		
		private Node findNodeParent(int key){
			Node nownode = this.root;
			if(nownode==null) return null;
			Node nextnode;
			if(nownode.getKey()>key){
				nextnode = nownode.left;
			} else {
				nextnode = nownode.right;
			}
			if(nextnode==null) return null;
			
			while(nextnode.getKey()!=key){
				if(nextnode.getKey()>key){
					nownode=nextnode;
					nextnode=nextnode.left;
				} else {
					nownode=nextnode;
					nextnode=nextnode.right;
				}
				if(nextnode==null) return null;
			}
			return nownode;
		}
		
		private Node findMinNodeParent(Node node){
			Node nownode = node;
			Node nextnode = nownode.left;
			if(nextnode==null) return null;
			
			while(nextnode.left!=null){
				nownode=nextnode;
				nextnode=nextnode.left;
			}
			return nownode;
		}
		
		public void printAll(){
		//全件をキーの昇順に印刷する
			Node node = this.root;
			if(node==null) return;
			printChild(node);
		}
		
		private void printChild(Node node){
			if(node.left!=null) printChild(node.left);
			System.out.println(node.getKey()+":"+node.getValue());
			if(node.right!=null) printChild(node.right);
			return;
		}
	}
}
