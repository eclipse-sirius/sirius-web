/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo and others.
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
import {
  RepresentationLoadingIndicator,
  Selection,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  FormBasedView,
  FormContext,
  FormHandle,
  GQLForm,
  GQLList,
  GQLRepresentationsEventPayload,
  GQLTree,
  GQLWidget,
  ListPropertySection,
  TreePropertySection,
} from '@eclipse-sirius/sirius-components-forms';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, MutableRefObject, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SynchronizationButton } from '../SynchronizationButton';
import { RepresentationsViewState } from './RepresentationsView.types';
import { useRepresentationsViewHandle } from './useRepresentationsViewHandle';
import { useRepresentationsViewSubscription } from './useRepresentationsViewSubscription';
import { GQLFormRefreshedEventPayload } from './useRepresentationsViewSubscription.types';

const useRepresentationsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  view: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    gap: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    alignItems: 'center',
    borderBottomColor: theme.palette.divider,
  },
  content: {
    overflow: 'auto',
  },
}));

const isList = (widget: GQLWidget | undefined): widget is GQLList => widget && widget.__typename === 'List';
const isTree = (widget: GQLWidget | undefined): widget is GQLTree => widget && widget.__typename === 'TreeWidget';
const isFormRefreshedEventPayload = (
  payload: GQLRepresentationsEventPayload
): payload is GQLFormRefreshedEventPayload => payload && payload.__typename === 'FormRefreshedEventPayload';

export const RepresentationsView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ id, editingContextId, readOnly }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const [state, setState] = useState<RepresentationsViewState>({
      form: null,
      objectIds: [],
      pinned: false,
    });

    const applySelection = (selection: Selection) => {
      const newObjetIds = selection.entries.map((entry) => entry.id);
      setState((prevState) => ({
        ...prevState,
        objectIds: newObjetIds,
      }));
    };

    const formBasedViewRef: MutableRefObject<FormHandle | null> = useRef<FormHandle | null>(null);
    useRepresentationsViewHandle(id, formBasedViewRef, applySelection, ref);

    const { selection } = useSelection();
    useEffect(() => {
      if (!state.pinned) {
        applySelection(selection);
      }
    }, [selection, state.pinned]);

    const skip = state.objectIds.length === 0;
    const { payload, complete, loading } = useRepresentationsViewSubscription(editingContextId, state.objectIds, skip);
    useEffect(() => {
      if (isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, form: payload.form }));
      }
    }, [payload]);

    const { classes } = useRepresentationsViewStyles();

    const extractPlainList = (editingContextId: string, form: GQLForm, readOnly: boolean): JSX.Element => {
      const widget: GQLWidget | undefined = form.pages[0]?.groups[0]?.widgets[0];
      if (isList(widget)) {
        return (
          <div className={classes.content}>
            <ListPropertySection
              editingContextId={editingContextId}
              formId={form.id}
              readOnly={readOnly}
              widget={widget}
            />
          </div>
        );
      } else if (isTree(widget)) {
        return (
          <div className={classes.content}>
            <TreePropertySection
              editingContextId={editingContextId}
              formId={form.id}
              readOnly={readOnly}
              widget={widget}
            />
          </div>
        );
      } else {
        return <div className={classes.content} />;
      }
    };

    const toolbar = (
      <SynchronizationButton
        pinned={state.pinned && !skip}
        onClick={() => setState((prevState) => ({ ...prevState, pinned: !prevState.pinned }))}
      />
    );

    let contents: JSX.Element = <></>;

    if (complete || skip) {
      contents = (
        <div className={classes.idle}>
          <Typography variant="subtitle2">No object selected</Typography>
        </div>
      );
    } else {
      contents = (
        <Box
          sx={{
            display: 'grid',
            gridTemplateRows: '1fr',
            gridTemplateColumns: '1fr',
          }}
          data-representation-kind="form-representation-list">
          {!state.form || loading ? (
            <Box sx={{ gridRow: '1', gridColumn: '1' }}>
              <RepresentationLoadingIndicator />
            </Box>
          ) : null}
          {state.form ? (
            <Box sx={{ gridRow: '1', gridColumn: '1' }}>
              <FormContext.Provider
                value={{
                  payload: payload,
                }}>
                <FormBasedView
                  editingContextId={editingContextId}
                  form={state.form}
                  initialSelectedPageId={null}
                  readOnly={readOnly}
                  postProcessor={extractPlainList}
                  ref={formBasedViewRef}
                />
              </FormContext.Provider>
            </Box>
          ) : null}
        </Box>
      );
    }

    return (
      <div className={classes.view}>
        <div className={classes.toolbar}>{toolbar}</div>
        <div className={classes.content}>{contents}</div>
      </div>
    );
  }
);
