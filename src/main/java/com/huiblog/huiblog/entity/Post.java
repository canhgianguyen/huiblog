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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "title")
    private String title;

    @Column(name = "meta_title")
    private String metaTitle;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "content", columnDefinition = "Text")
    private String content;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "updated")
    private Date updatedDate;

    @Column(name = "img")
    private String img;

    @Column(name = "view_num")
    private int viewNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
