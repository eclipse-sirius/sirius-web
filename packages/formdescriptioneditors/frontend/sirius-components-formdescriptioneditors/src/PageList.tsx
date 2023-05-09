/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { useMutation } from '@apollo/client';
import { Selection, Toast } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import Tab from '@material-ui/core/Tab';
import Tabs from '@material-ui/core/Tabs';
import Typography from '@material-ui/core/Typography';
import React, { useEffect, useState } from 'react';
import { addPageMutation, deletePageMutation, movePageMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddPageInput,
  GQLAddPageMutationData,
  GQLAddPageMutationVariables,
  GQLAddPagePayload,
  GQLDeletePageInput,
  GQLDeletePageMutationData,
  GQLDeletePageMutationVariables,
  GQLErrorPayload,
  GQLMovePageInput,
  GQLMovePageMutationData,
  GQLMovePageMutationVariables,
  GQLMovePagePayload,
} from './FormDescriptionEditorEventFragment.types';
import { Page } from './Page';
import { PageListProps, PageListState } from './PageList.types';

const isErrorPayload = (payload: GQLAddPagePayload | GQLMovePagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const usePageListStyles = makeStyles((theme) => ({
  rightDropArea: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    borderRadius: '10px',
    color: 'gray',
    height: '40px',
    width: '50%',
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
  },
  pagesList: {
    display: 'flex',
    justifyContent: 'left',
  },
  tabsRoot: {
    minHeight: theme.spacing(4),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  tabRoot: {
    minHeight: theme.spacing(4),
    textTransform: 'none',
  },
  tabLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    width: 'inherit',
  },
  tabLabelText: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const PageList = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  selection,
  setSelection,
}: PageListProps) => {
  const classes = usePageListStyles();

  const { pages } = formDescriptionEditor;

  const [state, setState] = useState<PageListState>({ message: null, selectedPage: pages[0], pages });
  const { message } = state;

  useEffect(() => {
    setState((prevState) => {
      const selectedPage = pages.find((page) => page.id === state.selectedPage.id);
      if (selectedPage) {
        return { ...prevState, selectedPage, pages };
      }
      return { ...prevState, selectedPage: pages[0], pages };
    });
  }, [pages, state.selectedPage.id]);

  const onChangeTab = (_: React.ChangeEvent<{}>, value: string) => {
    const selectedPage = pages.find((page) => page.id === value);
    setState((prevState) => {
      return { ...prevState, selectedPage };
    });
  };

  const [addPage, { loading: addPageLoading, data: addPageData, error: addPageError }] = useMutation<
    GQLAddPageMutationData,
    GQLAddPageMutationVariables
  >(addPageMutation);

  useEffect(() => {
    if (!addPageLoading) {
      if (addPageError) {
        setState((prevState) => {
          return { ...prevState, message: addPageError.message };
        });
      }
      if (addPageData) {
        const { addPage } = addPageData;
        if (isErrorPayload(addPage)) {
          setState((prevState) => {
            return { ...prevState, message: addPage.message };
          });
        }
      }
    }
  }, [addPageLoading, addPageData, addPageError]);

  const [movePage, { loading: movePageLoading, data: movePageData, error: movePageError }] = useMutation<
    GQLMovePageMutationData,
    GQLMovePageMutationVariables
  >(movePageMutation);

  useEffect(() => {
    if (!movePageLoading) {
      if (movePageError) {
        setState((prevState) => {
          return { ...prevState, message: movePageError.message };
        });
      }
      if (movePageData) {
        const { movePage } = movePageData;
        if (isErrorPayload(movePage)) {
          setState((prevState) => {
            return { ...prevState, message: movePage.message };
          });
        }
      }
    }
  }, [movePageLoading, movePageData, movePageError]);

  const [deletePage, { loading: deletePageLoading, data: deletePageData, error: deletePageError }] = useMutation<
    GQLDeletePageMutationData,
    GQLDeletePageMutationVariables
  >(deletePageMutation);

  useEffect(() => {
    if (!deletePageLoading) {
      if (deletePageError) {
        setState((prevState) => {
          return { ...prevState, message: deletePageError.message };
        });
      }
      if (deletePageData) {
        const { deletePage } = deletePageData;
        if (isErrorPayload(deletePage)) {
          setState((prevState) => {
            return { ...prevState, message: deletePage.message };
          });
        }
      }
    }
  }, [deletePageLoading, deletePageData, deletePageError]);

  const handleClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const currentPage = pages.find((page) => page.id === event.currentTarget.id);
    if (currentPage) {
      const newSelection: Selection = {
        entries: [
          {
            id: currentPage.id,
            label: currentPage.label,
            kind: `siriusComponents://semantic?domain=view&entity=PageDescription`,
          },
        ],
      };
      setSelection(newSelection);
    }
    event.stopPropagation();
  };

  const handleDelete: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    if (event.key === 'Delete') {
      const deletePageInput: GQLDeletePageInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: state.selectedPage.id,
      };
      const deletePageVariables: GQLDeletePageMutationVariables = { input: deletePageInput };
      deletePage({ variables: deletePageVariables });
      event.stopPropagation();
    }
  };

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('draggedElementId', event.currentTarget.id);
    event.dataTransfer.setData('draggedElementType', 'Page');
  };

  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDropTab: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const draggedElementId: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'Page') {
      return;
    }

    const dropLocationId: string = event.currentTarget.id;
    const index: number = pages.findIndex((p) => p.id === dropLocationId);

    if (index < 0) {
      //Drop location invalids
      return;
    }

    if (draggedElementId === 'Page') {
      const addPageInput: GQLAddPageInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        index,
      };
      const addPageVariables: GQLAddPageMutationVariables = { input: addPageInput };
      addPage({ variables: addPageVariables });
    } else if (formDescriptionEditor.pages.find((g) => g.id === draggedElementId)) {
      const movePageInput: GQLMovePageInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: draggedElementId,
        index,
      };
      const movePageVariables: GQLMovePageMutationVariables = { input: movePageInput };
      movePage({ variables: movePageVariables });
    }
  };

  const handleDropArea: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'Page') {
      return;
    }

    let index = formDescriptionEditor.pages.length;

    if (id === 'Page') {
      const addPageInput: GQLAddPageInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        index,
      };
      const addPageVariables: GQLAddPageMutationVariables = { input: addPageInput };
      addPage({ variables: addPageVariables });
    } else if (pages.find((g) => g.id === id)) {
      index--;
      const movePageInput: GQLMovePageInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: id,
        index,
      };
      const movePageVariables: GQLMovePageMutationVariables = { input: movePageInput };
      movePage({ variables: movePageVariables });
    }
  };

  return (
    <div>
      <div className={classes.pagesList}>
        <Tabs
          classes={{ root: classes.tabsRoot }}
          value={state.selectedPage.id}
          onChange={onChangeTab}
          variant="scrollable"
          scrollButtons="on"
          textColor="primary"
          indicatorColor="primary">
          {state.pages.map((page) => {
            return (
              <Tab
                {...a11yProps(page.id)}
                data-testid={`Page-${page.id}`}
                classes={{ root: classes.tabRoot }}
                id={page.id}
                value={page.id}
                label={
                  <div className={classes.tabLabel}>
                    <div className={classes.tabLabelText}>{page.label}</div>
                  </div>
                }
                key={page.id}
                onClick={handleClick}
                onKeyDown={handleDelete}
                draggable={true}
                onDragStart={handleDragStart}
                onDragEnter={handleDragEnter}
                onDragOver={handleDragOver}
                onDragLeave={handleDragLeave}
                onDrop={handleDropTab}
              />
            );
          })}
        </Tabs>
        <div
          data-testid="PageList-DropArea"
          className={classes.rightDropArea}
          onDragEnter={handleDragEnter}
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDropArea}>
          <Typography variant="body1">{'Drag and drop a page here'}</Typography>
        </div>
      </div>
      <Page
        editingContextId={editingContextId}
        page={state.selectedPage}
        formDescriptionEditor={formDescriptionEditor}
        representationId={representationId}
        selection={selection}
        setSelection={setSelection}
      />
      <Toast
        message={message}
        open={!!message}
        onClose={() =>
          setState((prevState) => {
            return { ...prevState, message: null };
          })
        }
      />
    </div>
  );
};
