package sage.domain.service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sage.domain.commons.DomainRuntimeException;
import sage.domain.commons.IdCommons;
import sage.domain.repository.GroupRepository;
import sage.domain.repository.GroupTopicRepository;
import sage.domain.repository.TagRepository;
import sage.domain.repository.UserRepository;
import sage.entity.Blog;
import sage.entity.Group;
import sage.entity.GroupTopic;
import sage.entity.Tag;

@Service
@Transactional
public class GroupService {
  @Autowired
  private GroupRepository groupRepo;
  @Autowired
  private GroupTopicRepository groupTopicRepo;
  @Autowired
  private TagRepository tagRepo;
  @Autowired
  private UserRepository userRepo;

  public Group getGroup(long id) {
    return groupRepo.get(id);
  }

  public Group newGroup(long userId, String name, String introduction, Collection<Long> tagIds) {
    Set<Tag> tags = tagRepo.byIds(tagIds);
    return groupRepo.save(new Group(name, introduction, tags, userRepo.load(userId), new Date()));
  }

  public GroupTopic getTopic(long id) {
    return groupTopicRepo.get(id);
  }

  public GroupTopic post(long userId, Blog blog) {
    if (!IdCommons.equal(userId, blog.getAuthor().getId())) {
      throw new DomainRuntimeException("Cannot post topic. User[%d] is not the author of Blog[%d]", userId, blog.getId());
    }
    return groupTopicRepo.save(new GroupTopic(blog));
  }

  public Collection<Group> byTags(Collection<Long> tagIds) {
    return groupRepo.byTags(tagRepo.byIds(tagIds));
  }

  public Collection<GroupTopic> topics(long groupId) {
    return groupTopicRepo.byGroup(groupId);
  }
}