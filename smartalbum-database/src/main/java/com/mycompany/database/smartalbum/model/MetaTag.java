/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package com.mycompany.database.smartalbum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;



/**
 * Class for representing MetaTag Entity
 *
 * @author Aristide M'vou
 */

@Entity
@NamedQueries({
@NamedQuery(name = "MetaTag.findPopularTags", query = "select new MetaTag(m.id, m.tag) from MetaTag m join m.images img group by m.id, m.tag order by count(img) desc"),
@NamedQuery(name = "MetaTag.findSuggestTags", query = "select m from MetaTag m where lower(m.tag) like :tag")
})
@Table(name =  "MetaTag",uniqueConstraints = {
	@UniqueConstraint(columnNames = "tag")})
public class MetaTag extends ABuisnessObject<Long> implements Serializable, Cloneable {

	private static final long serialVersionUID = -9065024051468971330L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 255, nullable = false)
	@NotEmpty
	@NotNull
	@Length(min = 3, max = 50)
	private String tag;

	@ManyToMany(mappedBy = "imageTags")
	private List<Image> images = new ArrayList<Image>();

	public MetaTag() {
	}

	public MetaTag(Long id, String tag) {
		this.id = id;
		this.tag = tag;
	}

	//---------------------------------Getters, Setters..
	public Long getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public void addImage(Image image) {
		images.add(image);
	}

	public void removeImage(Image image) {
		images.remove(image);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		final MetaTag metaTag = (MetaTag) obj;

		return (id == null ? metaTag.getId() == null : id.equals(metaTag.getId()))
				&& tag.equalsIgnoreCase(metaTag.getTag());
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + tag.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "{id : "+getId()+", tag : "+getTag()+"}"; 
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}
}
