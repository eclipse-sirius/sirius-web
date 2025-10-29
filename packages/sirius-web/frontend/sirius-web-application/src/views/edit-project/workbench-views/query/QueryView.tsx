/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
  IconOverlay,
  Selection,
  ServerContext,
  ServerContextValue,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Skeleton from '@mui/material/Skeleton';
import { SxProps, Theme, useTheme } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import {
  ComponentType,
  ForwardedRef,
  forwardRef,
  RefObject,
  useContext,
  useEffect,
  useImperativeHandle,
  useRef,
  useState,
} from 'react';
import { List as FixedSizeList, RowComponentProps } from 'react-window';
import { makeStyles } from 'tss-react/mui';
import { useCurrentProject } from '../../useCurrentProject';
import { SynchronizationButton } from '../SynchronizationButton';
import {
  ExportResultButtonProps,
  ExpressionAreaHandle,
  ExpressionAreaProps,
  ExpressionResultViewerProps,
  QueryViewConfiguration,
  QueryViewState,
  ResultAreaProps,
} from './QueryView.types';
import { useEvaluateExpression } from './useEvaluateExpression';
import {
  GQLBooleanExpressionResult,
  GQLIntExpressionResult,
  GQLObject,
  GQLObjectExpressionResult,
  GQLObjectsExpressionResult,
  GQLStringExpressionResult,
  GQLStringsExpressionResult,
} from './useEvaluateExpression.types';
import { useExpression } from './useExpression';
import { useQueryViewHandle } from './useQueryViewHandle';

const useQueryViewStyles = makeStyles()((theme) => ({
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
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    alignItems: 'center',
    borderBottomColor: theme.palette.divider,
  },
  content: {
    overflow: 'auto',
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  },
}));

export const QueryView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { id, editingContextId, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const queryViewStyle: SxProps<Theme> = (theme) => ({
      display: 'flex',
      flexDirection: 'column',
      gap: theme.spacing(2),
      paddingX: theme.spacing(1),
      overflow: 'auto',
    });

    const [state, setState] = useState<QueryViewState>({
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

    const expressionAreaRef: RefObject<ExpressionAreaHandle | null> = useRef<ExpressionAreaHandle | null>(null);
    useQueryViewHandle(id, expressionAreaRef, applySelection, ref);

    const { selection } = useSelection();
    useEffect(() => {
      if (!state.pinned) {
        applySelection(selection);
      }
    }, [selection, state.pinned]);

    const { classes } = useQueryViewStyles();
    const { evaluateExpression, loading, result } = useEvaluateExpression(state.objectIds);
    const handleEvaluateExpression = (expression: string) => evaluateExpression(editingContextId, expression);

    const initialQueryViewConfiguration: QueryViewConfiguration =
      initialConfiguration as unknown as QueryViewConfiguration;
    const initialQueryText = initialQueryViewConfiguration?.queryText ?? null;

    const toolbar = (
      <SynchronizationButton
        pinned={state.pinned}
        onClick={() => setState((prevState) => ({ ...prevState, pinned: !prevState.pinned }))}
      />
    );

    const contents: JSX.Element = (
      <Box data-representation-kind="query" sx={queryViewStyle}>
        <ExpressionArea
          editingContextId={editingContextId}
          initialQueryText={initialQueryText}
          onEvaluateExpression={handleEvaluateExpression}
          disabled={loading || readOnly}
          ref={expressionAreaRef}
        />
        <ResultArea loading={loading} payload={result} />
      </Box>
    );
    return (
      <div className={classes.view}>
        <div className={classes.toolbar}>{toolbar}</div>
        <div className={classes.content}>{contents}</div>
      </div>
    );
  }
);

const ExpressionArea = forwardRef<ExpressionAreaHandle, ExpressionAreaProps>(
  (
    { editingContextId, initialQueryText, onEvaluateExpression, disabled }: ExpressionAreaProps,
    ref: ForwardedRef<ExpressionAreaHandle>
  ) => {
    const { expression, onExpressionChange } = useExpression(editingContextId, initialQueryText);

    useImperativeHandle(
      ref,
      () => {
        return {
          getExpression: () => expression,
        };
      },
      [expression]
    );

    const expressionAreaToolbarStyle: SxProps<Theme> = {
      display: 'flex',
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'space-between',
    };

    const handleKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
      if ('Enter' === event.key && (event.ctrlKey || event.metaKey) && !disabled) {
        onEvaluateExpression(expression);
      }
    };

    var isApple = /(Mac|iPhone|iPod|iPad)/i.test(navigator.platform);

    return (
      <div data-role="expression">
        <Box sx={expressionAreaToolbarStyle}>
          <Typography variant="subtitle2">Expression</Typography>

          <Tooltip
            title={`Press this button or ${
              isApple ? '⌘ ' : 'Ctrl '
            } + Enter when the textfield below is focused to execute the expression`}>
            <IconButton
              onClick={() => onEvaluateExpression(expression)}
              color={expression.trim().length > 0 ? 'primary' : undefined}
              disabled={disabled || expression.trim().length === 0}>
              <PlayCircleOutlineIcon />
            </IconButton>
          </Tooltip>
        </Box>
        <div>
          <TextField
            placeholder="Enter an expression to get started"
            data-testid="query-textfield"
            value={expression}
            onChange={onExpressionChange}
            onKeyDown={handleKeyDown}
            disabled={disabled}
            spellCheck={false}
            multiline
            minRows={5}
            fullWidth
            autoFocus
            sx={{
              backgroundColor: '#fff8e5',
            }}
          />
        </div>
      </div>
    );
  }
);

const ObjectExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'ObjectExpressionResult') {
    return null;
  }

  const { objectValue } = result as GQLObjectExpressionResult;

  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const listItemIconStyle: SxProps<Theme> = () => ({
    minWidth: '0px',
  });
  return (
    <Box sx={{ overflow: 'auto' }}>
      <Typography variant="body2" gutterBottom>
        One object has been returned
      </Typography>
      <Box sx={(theme) => ({ display: 'flex', flexDirection: 'column', gap: theme.spacing(1), overflow: 'auto' })}>
        <List dense>
          <ListItem sx={listItemStyle}>
            <ListItemIcon sx={listItemIconStyle}>
              <IconOverlay iconURLs={objectValue.iconURLs} alt="Icon of the object" />
            </ListItemIcon>
            <ListItemText primary={objectValue.label} />
          </ListItem>
        </List>
        <Box sx={{ display: 'flex', flexDirection: 'row' }}>
          <ExportResultButton objectIds={[objectValue.id]} />
        </Box>
      </Box>
    </Box>
  );
};

const ObjectRow = ({ data, index, style }: RowComponentProps<{ data: GQLObject[] }>) => {
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const listItemIconStyle: SxProps<Theme> = () => ({
    minWidth: '0px',
  });

  const object = data[index];

  return (
    <ListItem key={index} style={style} sx={listItemStyle}>
      <ListItemIcon sx={listItemIconStyle}>
        <IconOverlay iconURLs={object.iconURLs} alt="Icon of the object" />
      </ListItemIcon>
      <ListItemText primary={object.label} />
    </ListItem>
  );
};

const ObjectsExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'ObjectsExpressionResult') {
    return null;
  }

  const { objectsValue } = result as GQLObjectsExpressionResult;

  const listStyle: SxProps<Theme> = (theme) => ({
    border: `1px solid ${theme.palette.divider}`,
    marginBottom: '1px',
    overflow: 'auto',
  });

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', overflow: 'auto' }}>
      <Typography variant="body2" gutterBottom>
        A collection of {objectsValue.length} object{objectsValue.length > 1 ? 's' : ''} has been returned
      </Typography>
      <Box
        sx={(theme) => ({
          display: 'flex',
          flexDirection: 'column',
          gap: theme.spacing(1),
          paddingBottom: theme.spacing(1),
          overflow: 'auto',
        })}>
        <List dense disablePadding sx={listStyle}>
          <FixedSizeList
            rowComponent={ObjectRow}
            rowProps={{ data: objectsValue }}
            rowCount={objectsValue.length}
            rowHeight={34}
          />
        </List>
        <Box sx={{ display: 'flex', flexDirection: 'row' }}>
          <ExportResultButton objectIds={objectsValue.map((objectValue) => objectValue.id)} />
        </Box>
      </Box>
    </Box>
  );
};

const BooleanExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'BooleanExpressionResult') {
    return null;
  }

  const { booleanValue } = result as GQLBooleanExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One boolean has been returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={booleanValue.toString()} />
        </ListItem>
      </List>
    </Box>
  );
};

const StringRow = ({ data, index, style }: RowComponentProps<{ data: string[] }>) => {
  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });

  const value = data[index];

  return (
    <ListItem key={index} style={style} sx={listItemStyle}>
      <ListItemText primary={value} />
    </ListItem>
  );
};

const StringsExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'StringsExpressionResult') {
    return null;
  }

  const { stringsValue } = result as GQLStringsExpressionResult;

  const listStyle: SxProps<Theme> = (theme) => ({
    border: `1px solid ${theme.palette.divider}`,
  });

  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        A collection of {stringsValue.length} string{stringsValue.length > 1 ? 's' : ''} has been returned
      </Typography>
      <List dense disablePadding sx={listStyle}>
        <FixedSizeList
          rowComponent={StringRow}
          rowProps={{ data: stringsValue }}
          rowCount={stringsValue.length}
          rowHeight={34}
        />
      </List>
    </Box>
  );
};

const StringExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'StringExpressionResult') {
    return null;
  }

  const { stringValue } = result as GQLStringExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One string has been returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={stringValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const IntExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'IntExpressionResult') {
    return null;
  }

  const { intValue } = result as GQLIntExpressionResult;
  return (
    <Box>
      <Typography variant="body2" gutterBottom>
        One integer has been returned
      </Typography>
      <List dense>
        <ListItem>
          <ListItemText primary={intValue} />
        </ListItem>
      </List>
    </Box>
  );
};

const VoidExpressionResultViewer = ({ result }: ExpressionResultViewerProps) => {
  if (result.__typename !== 'VoidExpressionResult') {
    return null;
  }

  return <Typography variant="body2">The evaluation of this expression has not returned any result</Typography>;
};

const resultType2viewer: Record<string, ComponentType<ExpressionResultViewerProps>> = {
  ObjectExpressionResult: ObjectExpressionResultViewer,
  ObjectsExpressionResult: ObjectsExpressionResultViewer,
  BooleanExpressionResult: BooleanExpressionResultViewer,
  StringsExpressionResult: StringsExpressionResultViewer,
  StringExpressionResult: StringExpressionResultViewer,
  IntExpressionResult: IntExpressionResultViewer,
  VoidExpressionResult: VoidExpressionResultViewer,
};

const LoadingViewer = () => {
  const theme = useTheme();

  const listItemStyle: SxProps<Theme> = (theme) => ({
    gap: theme.spacing(2),
  });
  const skeletonTextStyle: SxProps<Theme> = (theme) => ({
    fontSize: theme.typography.body1.fontSize,
    width: '60%',
  });
  return (
    <Box>
      <Skeleton variant="text" sx={{ fontSize: theme.typography.body2.fontSize }} />
      <List dense>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
        <ListItem sx={listItemStyle}>
          <Skeleton variant="circular" width={16} height={16} />
          <Skeleton variant="text" sx={skeletonTextStyle} />
        </ListItem>
      </List>
    </Box>
  );
};

const ResultArea = ({ loading, payload }: ResultAreaProps) => {
  const resultAreaToolbarStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  };

  const titleAreaStyle: SxProps<Theme> = {
    display: 'flex',
    flexDirection: 'column',
  };

  let content: JSX.Element | null = null;
  if (loading) {
    content = <LoadingViewer />;
  } else if (payload) {
    const Viewer = resultType2viewer[payload.result.__typename];
    if (Viewer) {
      content = <Viewer result={payload.result} />;
    } else {
      content = <Typography variant="body2">The expression has returned a result that is not supported</Typography>;
    }
  }

  return (
    <div data-role="result" style={{ overflow: 'auto', display: 'flex', flexDirection: 'column' }}>
      <Box sx={resultAreaToolbarStyle}>
        <Box sx={titleAreaStyle}>
          <Typography variant="subtitle2">Evaluation result</Typography>
        </Box>
      </Box>

      {content}
    </div>
  );
};

const ExportResultButton = ({ objectIds }: ExportResultButtonProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { project } = useCurrentProject();

  return (
    <Button
      data-testid="export-csv-button"
      variant="contained"
      color="primary"
      component="a"
      href={encodeURI(
        `${httpOrigin}/api/editingcontexts/${
          project.currentEditingContext.id
        }/objects?contentType=text/csv&objectIds=${objectIds.join(',')}`
      )}
      type="application/octet-stream">
      Export as CSV
    </Button>
  );
};
