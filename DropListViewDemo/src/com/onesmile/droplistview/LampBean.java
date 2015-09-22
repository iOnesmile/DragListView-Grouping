package com.onesmile.droplistview;

public class LampBean implements Comparable<LampBean>{

	private int id;
	private String name;	
	private int groupId;	// 组ID
	private boolean isTipBar;	// 是否是组名
	
	public LampBean() {
	}

	public LampBean(int id, String name, int groupId) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
	}
	
	public LampBean(int id, String name, int groupId, boolean isTipbar) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		this.isTipBar = isTipbar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean isTipBar() {
		return isTipBar;
	}

	public void setTipBar(boolean isTipBar) {
		this.isTipBar = isTipBar;
	}

	@Override
	public int compareTo(LampBean another) {
		if (another != null) {
			int code = another.getGroupId() - getGroupId();
            if (code == 0){
            	// 当是同一组时，判断是否是组名，如果是排序到最上面
                if (isTipBar)
                    return -1;
                else
                    code = getId() - another.getId();
            }
            return code;
		}
		return -1;
	}
}
