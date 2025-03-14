/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import { gql, useMutation } from '@apollo/client';
import {
  IconOverlay,
  SelectionEntry,
  theme,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Checkbox from '@mui/material/Checkbox';
import Typography from '@mui/material/Typography';
import { Theme } from '@mui/material/styles';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import { TreeItem as MuiTreeItem } from '@mui/x-tree-view/TreeItem';
import React, { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLTree } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditTreeCheckboxInput,
  GQLEditTreeCheckboxMutationData,
  GQLEditTreeCheckboxMutationVariables,
  GQLEditTreeCheckboxPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  TreeItemProps,
} from './TreePropertySection.types';

const useTreeItemWidgetStyles = makeStyles()((theme) => ({
  label: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    columnGap: theme.spacing(1),
  },
  checkbox: {
    color: theme.palette.primary.light,
    '&.Mui-checked': {
      color: theme.palette.primary.light,
    },
    '&.Mui-disabled': {
      color: theme.palette.text.disabled,
    },
    fill: theme.palette.primary.light,
    width: theme.spacing(2),
    height: theme.spacing(2),
  },
  content: {
    paddingLeft: 0,
    paddingTop: 0,
    paddingRight: 0,
    paddingBottom: 0,
  },
  disabled: {},
}));

const treeItemLabelStyle = (theme: Theme, readOnly: boolean): React.CSSProperties => {
  const treeItemLabelStyle: React.CSSProperties = {};
  if (readOnly) {
    treeItemLabelStyle.color = theme.palette.text.disabled;
  }
  return treeItemLabelStyle;
};
const isErrorPayload = (payload: GQLEditTreeCheckboxPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditTreeCheckboxPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const editTreeCheckboxMutation = gql`
  mutation editTreeCheckbox($input: EditTreeCheckboxInput!) {
    editTreeCheckbox(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const TreeItem = ({ treeItemId, node, nodes, readOnly, editingContextId, formId, widgetId }: TreeItemProps) => {
  const { classes } = useTreeItemWidgetStyles();
  const { setSelection } = useSelection();

  const [editTreeCheckbox, { loading, error, data }] =
    useMutation<GQLEditTreeCheckboxMutationData>(editTreeCheckboxMutation);

  const onChange = (event) => {
    const newValue = event.target.checked;
    const input: GQLEditTreeCheckboxInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      treeId: widgetId,
      checkboxId: node.id,
      newValue,
    };
    const variables: GQLEditTreeCheckboxMutationVariables = {
      input,
    };
    editTreeCheckbox({ variables });
  };

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editTreeCheckbox } = data;
        if (isErrorPayload(editTreeCheckbox) || isSuccessPayload(editTreeCheckbox)) {
          addMessages(editTreeCheckbox.messages);
        }
      }
    }
  }, [loading, error, data]);

  const handleClick: React.MouseEventHandler<HTMLDivElement> = () => {
    if (node.selectable) {
      const newSelection: SelectionEntry = {
        id: node.id,
      };
      setSelection({ entries: [newSelection] });
    }
  };

  const label = (
    <div className={classes.label} onClick={handleClick}>
      {node.checkable ? (
        <Checkbox
          name={node.label}
          checked={node.value}
          onChange={onChange}
          onClick={(event) => event.stopPropagation()}
          data-testid={node.label}
          disabled={readOnly}
          classes={{
            root: classes.checkbox,
            checked: classes.checkbox,
            disabled: classes.disabled,
          }}
        />
      ) : null}
      <IconOverlay iconURL={node.iconURL} alt={node.label} />
      <Typography style={treeItemLabelStyle(theme, readOnly)}>{node.label}</Typography>
      {node.endIconsURL.map((iconURL, index) => (
        <IconOverlay iconURL={iconURL} alt={node.label} key={index} />
      ))}
    </div>
  );

  const childNodes = nodes.filter((childNode) => childNode.parentId === node.id);
  return (
    <MuiTreeItem itemId={treeItemId} label={label} classes={{ content: classes.content }}>
      {childNodes.map((childNode, index) => {
        const childTreeItemId = `${treeItemId}/${index}`;
        return (
          <TreeItem
            treeItemId={childTreeItemId}
            node={childNode}
            nodes={nodes}
            key={childTreeItemId}
            readOnly={readOnly}
            aria-role="treeitem"
            editingContextId={editingContextId}
            formId={formId}
            widgetId={widgetId}
          />
        );
      })}
    </MuiTreeItem>
  );
};

export const TreePropertySection: PropertySectionComponent<GQLTree> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLTree>) => {
  let { nodes, expandedNodesIds } = widget;

  if (widget.nodes.length === 0) {
    expandedNodesIds = [];
    nodes = [
      {
        id: 'none',
        parentId: null,
        label: 'None',
        kind: 'siriusComponents://unknown',
        iconURL: [],
        endIconsURL: [],
        checkable: false,
        value: false,
        selectable: false,
      },
    ];
  }

  const rootNodes = nodes.filter((node) => !node.parentId);
  return (
    <div>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <SimpleTreeView
        slots={{ collapseIcon: ExpandMoreIcon, expandIcon: ChevronRightIcon }}
        defaultExpandedItems={expandedNodesIds}>
        {rootNodes.map((rootNode, index) => {
          const rootItemId = `${index}`;
          return (
            <TreeItem
              treeItemId={rootItemId}
              node={rootNode}
              nodes={nodes}
              key={rootItemId}
              readOnly={readOnly || widget.readOnly}
              editingContextId={editingContextId}
              formId={formId}
              widgetId={widget.id}
            />
          );
        })}
      </SimpleTreeView>
    </div>
  );
};
