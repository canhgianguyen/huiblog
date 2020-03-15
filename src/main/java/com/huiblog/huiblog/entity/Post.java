package com.huiblog.huiblog.entity;

import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Indexed

@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "category_id")
    private int categoryID;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "title")
    private String title;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;

    @Column(name = "img")
    private String img;

    @Column(name = "view_num")
    private int viewNum;
}
