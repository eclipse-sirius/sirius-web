/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import Checkbox from '@material-ui/core/Checkbox';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { TreeItem as MuiTreeItem } from '@material-ui/lab';
import TreeView from '@material-ui/lab/TreeView';
import React, { useEffect } from 'react';
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
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
  TreeItemProps,
} from './TreePropertySection.types';

const useTreeItemWidgetStyles = makeStyles((theme) => ({
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
  disabled: {},
}));

const treeItemLabelStyle = (theme: Theme, readOnly: boolean): React.CSSProperties => {
  const treeItemLabelStyle: React.CSSProperties = {};
  if (readOnly) {
    treeItemLabelStyle.color = theme.palette.text.disabled;
  }
  return treeItemLabelStyle;
};
const isErrorPayload = (
  payload: GQLEditTreeCheckboxPayload | GQLUpdateWidgetFocusPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLEditTreeCheckboxPayload | GQLUpdateWidgetFocusPayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

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

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const TreeItem = ({ node, nodes, readOnly, editingContextId, formId, widgetId }: TreeItemProps) => {
  const classes = useTreeItemWidgetStyles();
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
        label: node.label,
        kind: node.kind,
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
    <MuiTreeItem nodeId={node.id} label={label}>
      {childNodes.map((childNode) => (
        <TreeItem
          node={childNode}
          nodes={nodes}
          key={childNode.id}
          readOnly={readOnly}
          aria-role="treeitem"
          editingContextId={editingContextId}
          formId={formId}
          widgetId={widgetId}
        />
      ))}
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

  const { addErrorMessage, addMessages } = useMultiToast();

  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => {
    sendUpdateWidgetFocus(true);
  };

  const onBlur = (e) => {
    // if the blur was because of outside focus
    // currentTarget is the parent element, relatedTarget is the clicked element
    if (!e.currentTarget.contains(e.relatedTarget)) {
      sendUpdateWidgetFocus(false);
    }
  };

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
      <TreeView
        defaultCollapseIcon={<ExpandMoreIcon />}
        defaultExpanded={expandedNodesIds}
        defaultExpandIcon={<ChevronRightIcon />}
        onFocus={onFocus}
        onBlur={onBlur}>
        {rootNodes.map((rootNode) => (
          <TreeItem
            node={rootNode}
            nodes={nodes}
            key={rootNode.id}
            readOnly={readOnly || widget.readOnly}
            editingContextId={editingContextId}
            formId={formId}
            widgetId={widget.id}
          />
        ))}
      </TreeView>
    </div>
  );
};
