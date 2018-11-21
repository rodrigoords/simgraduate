package br.com.metrocamp.simgraduate.simulation.model;

import br.com.metrocamp.simgraduate.course.model.Course;
import br.com.metrocamp.simgraduate.subject.model.Subject;
import br.com.metrocamp.simgraduate.utils.base.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Table(name = "simulations")
@Entity
@Data
@NoArgsConstructor
public class Simulation extends BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String cep;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @ManyToMany
  @JoinTable(
          name = "simulations_subjects",
          joinColumns = {@JoinColumn(name = "id_simulation", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "id_subject", referencedColumnName = "id")}
  )
  private List<Subject> subjects;
}
